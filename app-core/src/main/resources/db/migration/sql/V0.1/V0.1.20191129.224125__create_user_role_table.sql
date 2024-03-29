CREATE SEQUENCE T_USER_ROLE_ID_SEQ INCREMENT BY 1 MINVALUE 1000 NO MAXVALUE START WITH 1000;
CREATE TABLE T_USER_ROLE
(
    USER_ROLE_ID BIGINT      NOT NULL DEFAULT NEXTVAL('T_USER_ROLE_ID_SEQ') PRIMARY KEY,
    USER_ID      BIGINT      NOT NULL,
    ROLE_ID      BIGINT      NOT NULL,
    CREATE_TIME  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UPDATE_TIME  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);