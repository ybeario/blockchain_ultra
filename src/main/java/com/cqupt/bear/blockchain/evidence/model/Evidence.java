package com.cqupt.bear.blockchain.evidence.model;

import io.reactivex.Flowable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.0.1.
 */
public class Evidence extends Contract {
    private static final String BINARY =
            "60806040526008805460ff191690553480156200001b57600080fd5b5060405162001eff38038062001eff833981018060405260808110156200004157600080fd5b8101908080516401000000008111156200005a57600080fd5b820160208101848111156200006e57600080fd5b81516401000000008111828201871017156200008957600080fd5b50509291906020018051640100000000811115620000a657600080fd5b82016020810184811115620000ba57600080fd5b8151640100000000811182820187101715620000d557600080fd5b50509291906020018051640100000000811115620000f257600080fd5b820160208101848111156200010657600080fd5b81516401000000008111828201871017156200012157600080fd5b505060209182015186519194509250620001429160019190870190620001c8565b50825162000158906004906020860190620001c8565b5081516200016e906005906020850190620001c8565b5060068054600160ff199182161790915560038054600160a060020a031916600160a060020a039390931692909217909155600280544260005561010060a860020a0319166101003302179091169055506200026d915050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200020b57805160ff19168380011785556200023b565b828001600101855582156200023b579182015b828111156200023b5782518255916020019190600101906200021e565b50620002499291506200024d565b5090565b6200026a91905b8082111562000249576000815560010162000254565b90565b611c82806200027d6000396000f3fe608060405234801561001057600080fd5b5060043610610128576000357c01000000000000000000000000000000000000000000000000000000009004806370788d14116100bf5780639031bbe91161008e5780639031bbe914610427578063905295e31461056557806399b956a01461058b578063d8270dce146105ea578063f851a4401461060457610128565b806370788d14146103b35780637ca1952d146103bb57806381052959146103d75780638da5cb5b1461040357610128565b806337771027116100fb578063377710271461029b578063479f7820146102c157806347a64f44146103675780635d0a8b8e1461038d57610128565b806306fdde031461012d5780630de5d4fb146101aa57806312424e3f146102355780632d1b4e121461023f575b600080fd5b61013561060c565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561016f578181015183820152602001610157565b50505050905090810190601f16801561019c5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6101b2610699565b604051808060200183151515158152602001828103825284818151815260200191508051906020019080838360005b838110156101f95781810151838201526020016101e1565b50505050905090810190601f1680156102265780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b61023d6109ff565b005b610247610b29565b604051808060200183600160a060020a0316600160a060020a0316815260200182810382528481815181526020019150805190602001908083836000838110156101f95781810151838201526020016101e1565b61023d600480360360208110156102b157600080fd5b5035600160a060020a0316610f7a565b61023d600480360360208110156102d757600080fd5b8101906020810181356401000000008111156102f257600080fd5b82018360208201111561030457600080fd5b8035906020019184600183028401116401000000008311171561032657600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955061116d945050505050565b61023d6004803603602081101561037d57600080fd5b5035600160a060020a0316611260565b61023d600480360360208110156103a357600080fd5b5035600160a060020a031661139b565b61013561153f565b6103c36115d6565b604080519115158252519081900360200190f35b6103df6115df565b604051808260058111156103ef57fe5b60ff16815260200191505060405180910390f35b61040b6115e8565b60408051600160a060020a039092168252519081900360200190f35b6103c36004803603606081101561043d57600080fd5b81019060208101813564010000000081111561045857600080fd5b82018360208201111561046a57600080fd5b8035906020019184600183028401116401000000008311171561048c57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295600160a060020a038535169590949093506040810192506020013590506401000000008111156104f057600080fd5b82018360208201111561050257600080fd5b8035906020019184600183028401116401000000008311171561052457600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506115fc945050505050565b61023d6004803603602081101561057b57600080fd5b5035600160a060020a0316611931565b6105b1600480360360208110156105a157600080fd5b5035600160a060020a0316611a50565b604051808360028111156105c157fe5b60ff1681526020018260018111156105d557fe5b60ff1681526020019250505060405180910390f35b6105f2611a6e565b60408051918252519081900360200190f35b61040b611a74565b60018054604080516020600284861615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156106915780601f1061066657610100808354040283529160200191610691565b820191906000526020600020905b81548152906001019060200180831161067457829003601f168201915b505050505081565b606060008033600090815260096020526040902054610100900460ff1660018111156106c157fe5b14610704576040805160e560020a62461bcd0281526020600482015260146024820152600080516020611c37833981519152604482015290519081900360640190fd5b6002805460ff16600581111561071657fe5b14806107325750600560025460ff16600581111561073057fe5b145b151561073d57600080fd5b60013360009081526009602052604090205460ff16600281111561075d57fe5b1480610786575060023360009081526009602052604090205460ff16600281111561078457fe5b145b806107a057506002546101009004600160a060020a031633145b15156107e05760405160e560020a62461bcd028152600401808060200182810382526034815260200180611c036034913960400191505060405180910390fd5b6008543360008181526009602090815260409182902054825160ff9586168015159382019390935292830184905230608084018190527f3a9c9c59da095c1418f7ec26d4760bb9f002a69f2317fba9ad73ca4cdacb105f95600795939460019390911690806060810160a0820184600281111561085957fe5b60ff1681526020848203810184528a54600260018216156101000260001901909116049082018190526040909101908a9080156108d75780601f106108ac576101008083540402835291602001916108d7565b820191906000526020600020905b8154815290600101906020018083116108ba57829003601f168201915b505083810382528654600260001961010060018416150201909116048082526020909101908790801561094b5780601f106109205761010080835404028352916020019161094b565b820191906000526020600020905b81548152906001019060200180831161092e57829003601f168201915b50509850505050505050505060405180910390a160085460078054604080516020601f60026000196001871615610100020190951694909404938401819004810282018101909252828152929360ff16929184918301828280156109f05780601f106109c5576101008083540402835291602001916109f0565b820191906000526020600020905b8154815290600101906020018083116109d357829003601f168201915b50505050509150915091509091565b600033600090815260096020526040902054610100900460ff166001811115610a2457fe5b14610a67576040805160e560020a62461bcd0281526020600482015260146024820152600080516020611c37833981519152604482015290519081900360640190fd5b6002546101009004600160a060020a03163314610ace576040805160e560020a62461bcd02815260206004820152601960248201527f4f6e6c79206f776e65722063616e2063616c6c20746869732e00000000000000604482015290519081900360640190fd5b6008805460ff19908116600190811790925560028054909116600517905560408051338152602081019290925280517fb3b7ba85540d88434a3a67dd5433bbe0b1f940485237ce0591f84eaf201682079281900390910190a1565b606060008033600090815260096020526040902054610100900460ff166001811115610b5157fe5b14610b94576040805160e560020a62461bcd0281526020600482015260146024820152600080516020611c37833981519152604482015290519081900360640190fd5b60008060025460ff166005811115610ba857fe5b14610bfd576040805160e560020a62461bcd02815260206004820152600e60248201527f496e76616c69642073746174652e000000000000000000000000000000000000604482015290519081900360640190fd5b60065460408051808201909152601281527f6b657953746174652069732066616c73652e0000000000000000000000000000602082015260ff9091161515600114908082610ccc5760405160e560020a62461bcd0281526004018080602001828103825283818151815260200191508051906020019080838360005b83811015610c91578181015183820152602001610c79565b50505050905090810190601f168015610cbe5780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b503360009081526009602052604090819020815180830190925280546002929190829060ff1684811115610cfc57fe5b6002811115610d0757fe5b81528154602090910190610100900460ff166001811115610d2457fe5b6001811115610d2f57fe5b905250816002811115610d3e57fe5b81516002811115610d4b57fe5b14610d8a5760405160e560020a62461bcd028152600401808060200182810382526034815260200180611c036034913960400191505060405180910390fd5b6040805130808252339282018390526080602083018181526005805460026001808316156101000260001901909216049386018490527f21e860c59a1478cc26711a86d783ec9b04838e3b526c38291c982b8b8eb1113a969495919493909290606083019060a084019087908015610e435780601f10610e1857610100808354040283529160200191610e43565b820191906000526020600020905b815481529060010190602001808311610e2657829003601f168201915b5050838103825284546002600019610100600184161502019091160480825260209091019085908015610eb75780601f10610e8c57610100808354040283529160200191610eb7565b820191906000526020600020905b815481529060010190602001808311610e9a57829003601f168201915b5050965050505050505060405180910390a1600280546001919060ff1916828002179055506005805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152309290918491830182828015610f665780601f10610f3b57610100808354040283529160200191610f66565b820191906000526020600020905b815481529060010190602001808311610f4957829003601f168201915b505050505091509650965050505050509091565b600033600090815260096020526040902054610100900460ff166001811115610f9f57fe5b14610fe2576040805160e560020a62461bcd0281526020600482015260146024820152600080516020611c37833981519152604482015290519081900360640190fd5b600354600160a060020a03163314611044576040805160e560020a62461bcd02815260206004820152601960248201527f4f6e6c792061646d696e2063616e2063616c6c20746869732e00000000000000604482015290519081900360640190fd5b6000600160a060020a038216600090815260096020526040902054610100900460ff16600181111561107257fe5b6040805180820190915260148152600080516020611c37833981519152602082015291149080826110e85760405160e560020a62461bcd02815260040180806020018281038252838181518152602001915080519060200190808383600083811015610c91578181015183820152602001610c79565b50600160a060020a038316600081815260096020908152604091829020805460ff191660029081179182905592519384527f074ffe655755f8e9ed8070a26dfff7bf6b7de4e823685ed4b580ada0b841ed3093879360ff90921692909190820190839081111561115457fe5b60ff1681526020019250505060405180910390a1505050565b600033600090815260096020526040902054610100900460ff16600181111561119257fe5b146111d5576040805160e560020a62461bcd0281526020600482015260146024820152600080516020611c37833981519152604482015290519081900360640190fd5b6002546101009004600160a060020a0316331461123c576040805160e560020a62461bcd02815260206004820152601960248201527f4f6e6c79206f776e65722063616e2063616c6c20746869732e00000000000000604482015290519081900360640190fd5b805161124f906005906020840190611b6a565b50506006805460ff19166001179055565b600033600090815260096020526040902054610100900460ff16600181111561128557fe5b146112c8576040805160e560020a62461bcd0281526020600482015260146024820152600080516020611c37833981519152604482015290519081900360640190fd5b600354600160a060020a0316331461132a576040805160e560020a62461bcd02815260206004820152601960248201527f4f6e6c792061646d696e2063616e2063616c6c20746869732e00000000000000604482015290519081900360640190fd5b600160a060020a038116600081815260096020908152604091829020805461ff00191661010017905590519182527f4d5bb83fa556c72e7862f1d78c701136db9071a560b1122916d7e19e50c142be918391600191908101825b60ff1681526020019250505060405180910390a150565b600033600090815260096020526040902054610100900460ff1660018111156113c057fe5b14611403576040805160e560020a62461bcd0281526020600482015260146024820152600080516020611c37833981519152604482015290519081900360640190fd5b6002546101009004600160a060020a0316331461146a576040805160e560020a62461bcd02815260206004820152601960248201527f4f6e6c79206f776e65722063616e2063616c6c20746869732e00000000000000604482015290519081900360640190fd5b6000600160a060020a038216600090815260096020526040902054610100900460ff16600181111561149857fe5b146114db576040805160e560020a62461bcd0281526020600482015260146024820152600080516020611c37833981519152604482015290519081900360640190fd5b600160a060020a038116600081815260096020908152604091829020805460ff19166001179081905591519283527f074ffe655755f8e9ed8070a26dfff7bf6b7de4e823685ed4b580ada0b841ed3092849260ff1691810182600281111561138457fe5b60048054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152606093909290918301828280156115cb5780601f106115a0576101008083540402835291602001916115cb565b820191906000526020600020905b8154815290600101906020018083116115ae57829003601f168201915b505050505090505b90565b60085460ff1681565b60025460ff1681565b6002546101009004600160a060020a031681565b60008033600090815260096020526040902054610100900460ff16600181111561162257fe5b14611665576040805160e560020a62461bcd0281526020600482015260146024820152600080516020611c37833981519152604482015290519081900360640190fd5b60018060025460ff16600581111561167957fe5b146116ce576040805160e560020a62461bcd02815260206004820152600e60248201527f496e76616c69642073746174652e000000000000000000000000000000000000604482015290519081900360640190fd5b3360009081526009602052604090819020815180830190925280546002929190829060ff16848111156116fd57fe5b600281111561170857fe5b81528154602090910190610100900460ff16600181111561172557fe5b600181111561173057fe5b90525081600281111561173f57fe5b8151600281111561174c57fe5b1461178b5760405160e560020a62461bcd028152600401808060200182810382526034815260200180611c036034913960400191505060405180910390fd5b600160a060020a03861630148015611836575060058054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152611836939092909183018282801561182b5780601f106118005761010080835404028352916020019161182b565b820191906000526020600020905b81548152906001019060200180831161180e57829003601f168201915b505050505086611a83565b1561192257865161184e9060079060208a0190611b6a565b506002805460ff1916811781556040805133808252309282018390526060602083018181526001805480821615610100026000190116969096049184018290527fc0d8a27aa74bbed83470bba99a3f110d5d4a6a3916a3a4ca96ecc0ef516e6b249592949293916080830190859080156119095780601f106118de57610100808354040283529160200191611909565b820191906000526020600020905b8154815290600101906020018083116118ec57829003601f168201915b505094505050505060405180910390a160019350611927565b600093505b5050509392505050565b600033600090815260096020526040902054610100900460ff16600181111561195657fe5b14611999576040805160e560020a62461bcd0281526020600482015260146024820152600080516020611c37833981519152604482015290519081900360640190fd5b600354600160a060020a031633146119fb576040805160e560020a62461bcd02815260206004820152601960248201527f4f6e6c792061646d696e2063616e2063616c6c20746869732e00000000000000604482015290519081900360640190fd5b600160a060020a0381166000818152600960209081526040808320805461ff0019169055519283527f4d5bb83fa556c72e7862f1d78c701136db9071a560b1122916d7e19e50c142be92849291810182611384565b60096020526000908152604090205460ff8082169161010090041682565b60005481565b600354600160a060020a031681565b6000816040516020018082805190602001908083835b60208310611ab85780518252601f199092019160209182019101611a99565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405160208183030381529060405280519060200120836040516020018082805190602001908083835b60208310611b265780518252601f199092019160209182019101611b07565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040528051906020012014905092915050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611bab57805160ff1916838001178555611bd8565b82800160010185558215611bd8579182015b82811115611bd8578251825591602001919060010190611bbd565b50611be4929150611be8565b5090565b6115d391905b80821115611be45760008155600101611bee56fe596f7520646f206e6f7420686176652073756666696369656e74207065726d697373696f6e7320746f2063616c6c20746869732e526f6c655374617465206973206c6f636b65642e000000000000000000000000a165627a7a72305820f64aaa5eb160f1cbaf930e27033be7836f932e46926a706a2cabc1dc6802a5310029";


    public static final String FUNC_NAME = "name";

    public static final String FUNC_ACQUIREANALYSISRESULT = "acquireAnalysisResult";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_ACQUIREKEYFORRESEARCH = "acquireKeyForResearch";

    public static final String FUNC_GIVERIGHTTORESEARCHER = "giveRightToResearcher";

    public static final String FUNC_RESETKEY = "resetKey";

    public static final String FUNC_LOCKACCOUNT = "lockAccount";

    public static final String FUNC_GIVERIGHTTOADMIN = "giveRightToAdmin";

    public static final String FUNC_QUERYEVIDENCEINFO = "queryEvidenceInfo";

    public static final String FUNC_OWNERAPPROVED = "ownerApproved";

    public static final String FUNC_EVIDENCESTATE = "evidenceState";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_UPLOADANALYSISRESULT = "uploadAnalysisResult";

    public static final String FUNC_UNLOCKACCOUNT = "unlockAccount";

    public static final String FUNC_USERS = "Users";

    public static final String FUNC_CREATIONTIME = "creationTime";

    public static final String FUNC_ADMIN = "admin";

    public static final Event ACCOUNTINFO_EVENT = new Event("AccountInfo",
            Arrays.asList(new TypeReference<Address>() {
            }, new TypeReference<Uint8>() {
            }));

    public static final Event AUTHORIZED_EVENT = new Event("Authorized",
            Arrays.asList(new TypeReference<Address>() {
            }, new TypeReference<Uint8>() {
            }));

    public static final Event ANALYZING_EVENT = new Event("Analyzing",
            Arrays.asList(new TypeReference<Address>() {
            }, new TypeReference<Utf8String>() {
            }, new TypeReference<Address>() {
            }, new TypeReference<Utf8String>() {
            }));

    public static final Event ANALYZED_EVENT = new Event("Analyzed",
            Arrays.asList(new TypeReference<Address>() {
            }, new TypeReference<Utf8String>() {
            }, new TypeReference<Address>() {
            }));

    public static final Event ACQUIRERESULT_EVENT = new Event("AcquireResult",
            Arrays.asList(new TypeReference<Utf8String>() {
            }, new TypeReference<Bool>() {
            }, new TypeReference<Address>() {
            }, new TypeReference<Utf8String>() {
            }, new TypeReference<Address>() {
            }, new TypeReference<Uint8>() {
            }));

    public static final Event OWNERAPPROVED_EVENT = new Event("OwnerApproved",
            Arrays.asList(new TypeReference<Address>() {
            }, new TypeReference<Bool>() {
            }));


    protected Evidence(String contractAddress, Web3j web3j, Credentials credentials,
                       ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }


    protected Evidence(String contractAddress, Web3j web3j, TransactionManager transactionManager,
                       ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> name() {
        final Function function = new Function(FUNC_NAME,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> acquireAnalysisResult() {
        final Function function = new Function(
                FUNC_ACQUIREANALYSISRESULT,
                Arrays.asList(),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> approve() {
        final Function function = new Function(
                FUNC_APPROVE,
                Arrays.asList(),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> acquireKeyForResearch() {
        final Function function = new Function(
                FUNC_ACQUIREKEYFORRESEARCH,
                Arrays.asList(),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> giveRightToResearcher(String user) {
        final Function function = new Function(
                FUNC_GIVERIGHTTORESEARCHER,
                Arrays.asList(new Address(user)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> resetKey(String _key) {
        final Function function = new Function(
                FUNC_RESETKEY,
                Arrays.asList(new Utf8String(_key)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> lockAccount(String account) {
        final Function function = new Function(
                FUNC_LOCKACCOUNT,
                Arrays.asList(new Address(account)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> giveRightToAdmin(String user) {
        final Function function = new Function(
                FUNC_GIVERIGHTTOADMIN,
                Arrays.asList(new Address(user)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> queryEvidenceInfo() {
        final Function function = new Function(FUNC_QUERYEVIDENCEINFO,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Boolean> ownerApproved() {
        final Function function = new Function(FUNC_OWNERAPPROVED,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<BigInteger> evidenceState() {
        final Function function = new Function(FUNC_EVIDENCESTATE,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Uint8>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> uploadAnalysisResult(String _AnalysisResult, String evidenceID, String _key) {
        final Function function = new Function(
                FUNC_UPLOADANALYSISRESULT,
                Arrays.asList(new Utf8String(_AnalysisResult),
                        new Address(evidenceID),
                        new Utf8String(_key)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> unlockAccount(String account) {
        final Function function = new Function(
                FUNC_UNLOCKACCOUNT,
                Arrays.asList(new Address(account)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple2<BigInteger, BigInteger>> Users(String param0) {
        final Function function = new Function(FUNC_USERS,
                Arrays.asList(new Address(param0)),
                Arrays.asList(new TypeReference<Uint8>() {
                }, new TypeReference<Uint8>() {
                }));
        return new RemoteCall<Tuple2<BigInteger, BigInteger>>(
                new Callable<Tuple2<BigInteger, BigInteger>>() {
                    @Override
                    public Tuple2<BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> creationTime() {
        final Function function = new Function(FUNC_CREATIONTIME,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> admin() {
        final Function function = new Function(FUNC_ADMIN,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public List<AccountInfoEventResponse> getAccountInfoEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ACCOUNTINFO_EVENT, transactionReceipt);
        ArrayList<AccountInfoEventResponse> responses = new ArrayList<AccountInfoEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AccountInfoEventResponse typedResponse = new AccountInfoEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.account = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.roleState = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AccountInfoEventResponse> accountInfoEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, AccountInfoEventResponse>() {
            @Override
            public AccountInfoEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(ACCOUNTINFO_EVENT, log);
                AccountInfoEventResponse typedResponse = new AccountInfoEventResponse();
                typedResponse.log = log;
                typedResponse.account = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.roleState = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AccountInfoEventResponse> accountInfoEventFlowable(DefaultBlockParameter startBlock,
                                                                       DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ACCOUNTINFO_EVENT));
        return accountInfoEventFlowable(filter);
    }

    public List<AuthorizedEventResponse> getAuthorizedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(AUTHORIZED_EVENT, transactionReceipt);
        ArrayList<AuthorizedEventResponse> responses = new ArrayList<AuthorizedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AuthorizedEventResponse typedResponse = new AuthorizedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.role = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AuthorizedEventResponse> authorizedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, AuthorizedEventResponse>() {
            @Override
            public AuthorizedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(AUTHORIZED_EVENT, log);
                AuthorizedEventResponse typedResponse = new AuthorizedEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.role = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AuthorizedEventResponse> authorizedEventFlowable(DefaultBlockParameter startBlock,
                                                                     DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AUTHORIZED_EVENT));
        return authorizedEventFlowable(filter);
    }

    public List<AnalyzingEventResponse> getAnalyzingEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ANALYZING_EVENT, transactionReceipt);
        ArrayList<AnalyzingEventResponse> responses = new ArrayList<AnalyzingEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AnalyzingEventResponse typedResponse = new AnalyzingEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.block = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.key = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AnalyzingEventResponse> analyzingEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, AnalyzingEventResponse>() {
            @Override
            public AnalyzingEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(ANALYZING_EVENT, log);
                AnalyzingEventResponse typedResponse = new AnalyzingEventResponse();
                typedResponse.log = log;
                typedResponse.block = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.key = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.name = (String) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AnalyzingEventResponse> analyzingEventFlowable(DefaultBlockParameter startBlock,
                                                                   DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ANALYZING_EVENT));
        return analyzingEventFlowable(filter);
    }

    public List<AnalyzedEventResponse> getAnalyzedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ANALYZED_EVENT, transactionReceipt);
        ArrayList<AnalyzedEventResponse> responses = new ArrayList<AnalyzedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AnalyzedEventResponse typedResponse = new AnalyzedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.block = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AnalyzedEventResponse> analyzedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, AnalyzedEventResponse>() {
            @Override
            public AnalyzedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(ANALYZED_EVENT, log);
                AnalyzedEventResponse typedResponse = new AnalyzedEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.name = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.block = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AnalyzedEventResponse> analyzedEventFlowable(DefaultBlockParameter startBlock,
                                                                 DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ANALYZED_EVENT));
        return analyzedEventFlowable(filter);
    }

    public List<AcquireResultEventResponse> getAcquireResultEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ACQUIRERESULT_EVENT, transactionReceipt);
        ArrayList<AcquireResultEventResponse> responses = new ArrayList<AcquireResultEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AcquireResultEventResponse typedResponse = new AcquireResultEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.analysisResult = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.block = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.role = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AcquireResultEventResponse> acquireResultEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, AcquireResultEventResponse>() {
            @Override
            public AcquireResultEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(ACQUIRERESULT_EVENT, log);
                AcquireResultEventResponse typedResponse = new AcquireResultEventResponse();
                typedResponse.log = log;
                typedResponse.analysisResult = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.name = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.block = (String) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.role = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AcquireResultEventResponse> acquireResultEventFlowable(DefaultBlockParameter startBlock,
                                                                           DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ACQUIRERESULT_EVENT));
        return acquireResultEventFlowable(filter);
    }

    public List<OwnerApprovedEventResponse> getOwnerApprovedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERAPPROVED_EVENT, transactionReceipt);
        ArrayList<OwnerApprovedEventResponse> responses = new ArrayList<OwnerApprovedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            OwnerApprovedEventResponse typedResponse = new OwnerApprovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.approve = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnerApprovedEventResponse> ownerApprovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, OwnerApprovedEventResponse>() {
            @Override
            public OwnerApprovedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERAPPROVED_EVENT, log);
                OwnerApprovedEventResponse typedResponse = new OwnerApprovedEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.approve = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnerApprovedEventResponse> ownerApprovedEventFlowable(DefaultBlockParameter startBlock,
                                                                           DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERAPPROVED_EVENT));
        return ownerApprovedEventFlowable(filter);
    }


    public static Evidence load(String contractAddress, Web3j web3j, Credentials credentials,
                                ContractGasProvider contractGasProvider) {
        return new Evidence(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Evidence load(String contractAddress, Web3j web3j, TransactionManager transactionManager,
                                ContractGasProvider contractGasProvider) {
        return new Evidence(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Evidence> deploy(Web3j web3j, Credentials credentials,
                                              ContractGasProvider contractGasProvider, String _name, String _info,
                                              String _key, String _admin) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(new Utf8String(_name),
                new Utf8String(_info),
                new Utf8String(_key),
                new Address(_admin)));
        return deployRemoteCall(Evidence.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Evidence> deploy(Web3j web3j, TransactionManager transactionManager,
                                              ContractGasProvider contractGasProvider, String _name, String _info,
                                              String _key, String _admin) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(new Utf8String(_name),
                new Utf8String(_info),
                new Utf8String(_key),
                new Address(_admin)));
        return deployRemoteCall(Evidence.class, web3j, transactionManager, contractGasProvider, BINARY,
                encodedConstructor);
    }


    public static class AccountInfoEventResponse {
        public Log log;

        public String account;

        public BigInteger roleState;
    }

    public static class AuthorizedEventResponse {
        public Log log;

        public String user;

        public BigInteger role;
    }

    public static class AnalyzingEventResponse {
        public Log log;

        public String block;

        public String key;

        public String user;

        public String name;
    }

    public static class AnalyzedEventResponse {
        public Log log;

        public String user;

        public String name;

        public String block;
    }

    public static class AcquireResultEventResponse {
        public Log log;

        public String analysisResult;

        public Boolean approved;

        public String user;

        public String name;

        public String block;

        public BigInteger role;
    }

    public static class OwnerApprovedEventResponse {
        public Log log;

        public String user;

        public Boolean approve;
    }
}
