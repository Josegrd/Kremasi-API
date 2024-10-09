package com.gardio.kremasi.constant;

public class PathApi {

    public static class  BasePath {
        public static final String EMPLOYEE = "/api/v1/employee" ;
        public static final String AUTH = "/api/v1/auth" ;
        public static final String IMAGE = "/api/v1/image" ;
        public static final String LOAN = "/api/v1/loan" ;
        public static final String NASABAH = "/api/v1/nasabah" ;
        public static final String NASABAH_PROFILE = "/api/v1/nasabah_profile" ;
        public static final String SAVING = "/api/v1/saving" ;
        public static final String TRX_SAVING = "/api/v1/trx_saving" ;
        public static final String LOAN_PAYMENT = "/api/v1/loan_payment" ;
        public static final String INSTALLMENT_TYPE = "/api/v1/installment_type" ;
        public static final String LOAN_TYPE = "/api/v1/loan_type" ;
    }

    public static class SubBashPath {
        public static final String SIGNUP = "/signup" ;
        public static final String SIGNIN = "/signin" ;
        public static final String CHECK_EMAIL = "/check-email" ;
        public static final String BY_ID = "/{id}" ;
        public static final String IMAGE_ID = "/{imageId}";
        public static final String APPROVE_LOAN = "/approved/{id}";
        public static final String GET_SAVING_BY_NASABAH_ID = "/nasabah/{id}";
    }
}
