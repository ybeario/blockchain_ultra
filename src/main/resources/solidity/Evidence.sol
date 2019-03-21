pragma solidity >=0.4.22 <0.6.0;

contract Evidence {
    uint public creationTime;
    string public name;
    State public evidenceState;
    address public owner;
    address public admin;
    string evidenceInfo;
    string private key;
    bool private keyState;
    string private analysisResult;
    bool public ownerApproved = false;

    enum State {Created, Analyzing, Analyzed, Locked, Inactive, Finished}
    enum Role {User, Admin, Researcher}
    enum RoleState {Normal, Locked}

    struct User {
        Role role;
        RoleState roleState;
    }

    mapping(address => User) public Users;

    constructor(string memory _name, string memory _info, string memory _key, address _admin) public {
        name = _name;
        evidenceInfo = _info;
        key = _key;
        keyState = true;
        admin = _admin;
        //这是一个默认账户
        owner = msg.sender;
        creationTime = now;
        evidenceState = State.Created;
    }

    modifier condition(bool _condition, string memory info) {
        require(_condition, info);
        _;
    }

    modifier onlyOwner() {
        require(
            msg.sender == owner,
            "Only owner can call this."
        );
        _;
    }

    modifier onlyAdmin(){
        require(
            msg.sender == admin,
            "Only admin can call this."
        );
        _;
    }

    modifier inState(State _state) {
        require(
            evidenceState == _state,
            "Invalid state."
        );
        _;
    }

    modifier hasRole(Role role, User memory user){
        require(
            user.role == role,
            "You do not have sufficient permissions to call this."
        );
        _;
    }

    modifier checkRoleState() {
        require(
            Users[msg.sender].roleState == RoleState.Normal,
            "RoleState is locked."
        );
        _;
    }

    event AccountInfo(address account, RoleState roleState);
    event Authorized(address user, Role role);
    event Analyzing(address block, string key, address user, string name);
    event Analyzed(address user, string name, address block);
    event AcquireResult(string analysisResult, bool approved, address user, string name, address block, Role role);
    event OwnerApproved(address user, bool approve);

    function giveRightToAdmin(address user) public checkRoleState onlyOwner {
        require(
            Users[user].roleState == RoleState.Normal,
            "RoleState is locked."
        );
        Users[user].role = Role.Admin;
        emit Authorized(user, Users[user].role);

    }

    function giveRightToResearcher(address user) public checkRoleState onlyAdmin condition(
        Users[user].roleState == RoleState.Normal,
        "RoleState is locked."
    ) {
        Users[user].role = Role.Researcher;
        emit Authorized(user, Users[user].role);

    }

    function lockAccount(address account) public checkRoleState onlyAdmin {
        Users[account].roleState = RoleState.Locked;
        emit AccountInfo(account, RoleState.Locked);
    }

    function unlockAccount(address account) public checkRoleState onlyAdmin {
        Users[account].roleState = RoleState.Normal;
        emit AccountInfo(account, RoleState.Normal);
    }

    function queryEvidenceInfo() public view returns (string memory) {
        return evidenceInfo;
    }

    function acquireKeyForResearch() public checkRoleState inState(State.Created) condition(
        keyState == true,
        "keyState is false."
    ) hasRole(
        Role.Researcher,
        Users[msg.sender]
    ) returns (string memory, address account){
        emit Analyzing(address(this), key, msg.sender, name);
        evidenceState = State.Analyzing;
        return (key, address(this));
    }

    function uploadAnalysisResult(string memory _AnalysisResult, address evidenceID, string memory _key) public checkRoleState inState(State
        .Analyzing) hasRole(
        Role.Researcher,
        Users[msg.sender]
    ) returns (bool success){
        if (evidenceID == address(this) && hashCompare(key, _key)) {
            analysisResult = _AnalysisResult;
            evidenceState = State.Analyzed;
            emit Analyzed(msg.sender, name, address(this));
            return true;
        }
        return false;
    }

    function acquireAnalysisResult() checkRoleState public returns (string memory, bool){

        require(
            evidenceState == State.Analyzed || evidenceState == State.Finished
        );

        require(
            Users[msg.sender].role == Role.Admin || Users[msg.sender].role == Role.Researcher || msg.sender == owner,
            "You do not have sufficient permissions to call this."
        );
        emit AcquireResult(analysisResult, ownerApproved, msg.sender, name, address(this), Users[msg.sender].role);
        return (analysisResult, ownerApproved);
    }

    function resetKey(string memory _key) checkRoleState onlyOwner public {
        key = _key;
        keyState = true;
    }

    function approve() checkRoleState onlyOwner public {
        ownerApproved = true;
        evidenceState = State.Finished;
        emit OwnerApproved(msg.sender, true);
    }

    function hashCompare(string memory a, string memory b) internal returns (bool) {
        return keccak256(abi.encodePacked(a)) == keccak256(abi.encodePacked(b));
    }
}





