package com.vlm.ui.chaincode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.hyperledger.fabric.protos.common.Common.Status;
import org.hyperledger.fabric.sdk.BlockEvent;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.QueryByChaincodeRequest;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.InvokeException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vlm.ui.support.AppUser;

/**
 * <h1>HFJavaSDKBasicExample</h1>
 * <p>
 * Simple example showcasing basic fabric-ca and fabric actions.
 * The demo required fabcar fabric up and running.
 * <p>
 * The demo shows
 * <ul>
 * <li>connecting to fabric-ca</li>
 * <li>enrolling admin to get new key-pair, certificate</li>
 * <li>registering and enrolling a new user using admin</li>
 * <li>creating HF client and initializing channel</li>
 * <li>invoking chaincode query</li>
 * </ul>
 *
 * @author lkolisko
 */
@Component
public class HlfClient {

 	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HlfClient.class);

	private static final org.slf4j.Logger log=logger;
    private   HFClient client = null;
    @PostConstruct
    public  void init() throws Exception {
        // create fabric-ca client
        HFCAClient caClient = getHfCaClient("http://192.168.99.100:7054", null);

        // enroll or load admin
        AppUser admin = getAdmin(caClient);
        log.info(admin+"");

        // register and enroll new user
        AppUser appUser = getUser(caClient, admin, "hfuser1");
        log.info(appUser+"");

        // get HFC client instance
        client = getHfClient();
        // set user contextinv
        client.setUserContext(admin);

        // get HFC channel using the client
        Channel channel = getChannel(client);
        log.info("Channel: " + channel.getName());

        // call query blockchain example
        //invokeBlockChain(client);
    }

    
    

    /**
     * Invoke blockchain query
     *
     * @param client The HF Client
     * @throws ProposalException
     * @throws InvalidArgumentException
     */
    public String queryBlockChain(String action,String argument) throws ProposalException, InvalidArgumentException {
        // get channel instance from client
        Channel channel = client.getChannel("siamchannel");
        // create chaincode request
        QueryByChaincodeRequest qpr = client.newQueryProposalRequest();
        // build cc id providing the chaincode name. Version is omitted here.
        ChaincodeID fabcarCCId = ChaincodeID.newBuilder().setName("siamcc").build();
        qpr.setChaincodeID(fabcarCCId);
        // CC function to be called
        qpr.setFcn(action);
        qpr.setArgs(argument);
         Collection<ProposalResponse> responses = channel.queryByChaincode(qpr);
        // display response
         
         for (ProposalResponse resp : responses) {
             if (resp.getProposalResponse() == null || resp.getProposalResponse().getResponse() == null) {
                 logger.warn("Wrong proposal response {} from peer {}", resp, resp.getPeer().getName());
                  continue;
             }
             if (resp.getProposalResponse().getResponse().getStatus() == Status.SUCCESS.getNumber()) {
                 logger.debug("Query response status {} msg {} payload {}", resp.getProposalResponse().getResponse().getStatus(),
                         resp.getProposalResponse().getResponse().getMessage(),
                         resp.getProposalResponse().getResponse().getPayload().toStringUtf8());
                 String repsonse= resp.getProposalResponse().getResponse().getPayload().toStringUtf8();
                 return repsonse;
             }
         }

         return StringUtils.EMPTY;
         
    }

    
    public void invokeBlockChain(String action,String payload) throws ProposalException, InvalidArgumentException, JsonProcessingException {
        // get channel instance from client
    	String chName="siamchannel";
        Channel channel = client.getChannel(chName);
        // create chaincode request
        TransactionProposalRequest transactionProposalRequest = client.newTransactionProposalRequest();
        // build cc id providing the chaincode name. Version is omitted here.
        ChaincodeID fabcarCCId = ChaincodeID.newBuilder().setName("siamcc").build();
        transactionProposalRequest.setChaincodeID(fabcarCCId);
        // CC function to be called
        transactionProposalRequest.setFcn(action);
        transactionProposalRequest.setArgs(payload);
         Collection<ProposalResponse> responses = channel.sendTransactionProposal(transactionProposalRequest);
        // display response

         // Verifying responses
         for (ProposalResponse proposal : responses) {
             if (!proposal.isVerified()) {
                 logger.warn("Invalid proposal {}", proposal);
                 throw new RuntimeException(String.format("Invalid proposal %s", proposal));
             }
         }
         
         // Sending transaction to orderers
         logger.debug("Sending transaction for {} {}", action, payload);
         Channel.NOfEvents nofEvents = Channel.NOfEvents.createNofEvents();
         CompletableFuture<BlockEvent.TransactionEvent> txFuture;
         try {
             if (!channel.getPeers(EnumSet.of(Peer.PeerRole.EVENT_SOURCE)).isEmpty()) {
                 nofEvents.addPeers(channel.getPeers(EnumSet.of(Peer.PeerRole.EVENT_SOURCE)));
             }
             txFuture = channel.sendTransaction(responses,
                     Channel.TransactionOptions.createTransactionOptions()
                             .orderers(channel.getOrderers())
                             .shuffleOrders(false)
                             .nOfEvents(nofEvents));
         } catch (Exception e) {
             logger.warn("Exception during send transaction", e);
             throw new InvokeException("During send transaction", e);
         }

    }
    
    
    /**
     * Initialize and get HF channel
     *
     * @param client The HFC client
     * @return Initialized channel
     * @throws InvalidArgumentException
     * @throws TransactionException
     */
    static Channel getChannel(HFClient client) throws InvalidArgumentException, TransactionException {
        // initialize channel
        // peer name and endpoint in fabcar network
        Peer peer = client.newPeer("peer0.manufacturer.siam.com:", "grpc://192.168.99.100:7051");
        // eventhub name and endpoint in fabcar network
        EventHub eventHub = client.newEventHub("eventhub01", "grpc://192.168.99.100:7053");
        // orderer name and endpoint in fabcar network
        Orderer orderer = client.newOrderer("orderer.example.com", "grpc://192.168.99.100:7050");
        // channel name in fabcar network
        Channel channel = client.newChannel("siamchannel");
        channel.addPeer(peer);
        channel.addEventHub(eventHub);
        channel.addOrderer(orderer);
        channel.initialize();
        return channel;
    }

    /**
     * Create new HLF client
     *
     * @return new HLF client instance. Never null.
     * @throws CryptoException
     * @throws InvalidArgumentException
     */
    static HFClient getHfClient() throws Exception {
        // initialize default cryptosuite
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        // setup the client
        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(cryptoSuite);
        return client;
    }


    /**
     * Register and enroll user with userId.
     * If AppUser object with the name already exist on fs it will be loaded and
     * registration and enrollment will be skipped.
     *
     * @param caClient  The fabric-ca client.
     * @param registrar The registrar to be used.
     * @param userId    The user id.
     * @return AppUser instance with userId, affiliation,mspId and enrollment set.
     * @throws Exception
     */
    static AppUser getUser(HFCAClient caClient, AppUser registrar, String userId) throws Exception {
        AppUser appUser = tryDeserialize(userId);
        if (appUser == null) {
            RegistrationRequest rr = new RegistrationRequest(userId, "org1.department1");
            String enrollmentSecret = caClient.register(rr, registrar);
            Enrollment enrollment = caClient.enroll(userId, enrollmentSecret);
            appUser = new AppUser(userId, "manufacturer", "manufacturerMSP", enrollment);
            serialize(appUser);
        }
        return appUser;
    }

    /**
     * Enroll admin into fabric-ca using {@code admin/adminpw} credentials.
     * If AppUser object already exist serialized on fs it will be loaded and
     * new enrollment will not be executed.
     *
     * @param caClient The fabric-ca client
     * @return AppUser instance with userid, affiliation, mspId and enrollment set
     * @throws Exception
     */
    static AppUser getAdmin(HFCAClient caClient) throws Exception {
        AppUser admin = tryDeserialize("admin");
        if (admin == null) {
            Enrollment adminEnrollment = caClient.enroll("admin", "adminpw");
            admin = new AppUser("admin", "manufacturer", "manufacturerMSP", adminEnrollment);
            serialize(admin);
        }
        return admin;
    }

    /**
     * Get new fabic-ca client
     *
     * @param caUrl              The fabric-ca-server endpoint url
     * @param caClientProperties The fabri-ca client properties. Can be null.
     * @return new client instance. never null.
     * @throws Exception
     */
    static HFCAClient getHfCaClient(String caUrl, Properties caClientProperties) throws Exception {
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        HFCAClient caClient = HFCAClient.createNewInstance(caUrl, caClientProperties);
        caClient.setCryptoSuite(cryptoSuite);
        return caClient;
    }


    // user serialization and deserialization utility functions
    // files are stored in the base directory

    /**
     * Serialize AppUser object to file
     *
     * @param appUser The object to be serialized
     * @throws IOException
     */
    static void serialize(AppUser appUser) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(
                Paths.get(appUser.getName() + ".jso")))) {
            oos.writeObject(appUser);
        }
    }

    /**
     * Deserialize AppUser object from file
     *
     * @param name The name of the user. Used to build file name ${name}.jso
     * @return
     * @throws Exception
     */
    static AppUser tryDeserialize(String name) throws Exception {
        if (Files.exists(Paths.get(name + ".jso"))) {
            return deserialize(name);
        }
        return null;
    }

    static AppUser deserialize(String name) throws Exception {
        try (ObjectInputStream decoder = new ObjectInputStream(
                Files.newInputStream(Paths.get(name + ".jso")))) {
            return (AppUser) decoder.readObject();
        }
    }
    
    public static void main(String[] args) throws Exception {
    	HlfClient client = new HlfClient();
    	client.init();
    	System.out.println( client.queryBlockChain("getRepair", "VWVK201101"));
	}
}
