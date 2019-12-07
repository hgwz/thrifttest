namespace java com.testerhome.service

// compute
enum ComputeType {
    ADD = 0;
}

// request+++
struct ComputeRequest {
    1:required i64 x;
    2:required i64 y;
    3:required ComputeType computeType;
}

// response
struct ComputeResponse {
    1:required i32 errorNo;
    2:optional string errorMsg;
    3:required i64 computeRet;
}

service ComputeServer {
    ComputeResponse getComputeResult(1:ComputeRequest request);
}