CREATE TABLE IF NOT EXISTS oauth_client_details (
  client_id               VARCHAR(255) PRIMARY KEY,
  resource_ids            VARCHAR(256),
  client_secret           VARCHAR(256),
  scope                   VARCHAR(256),
  authorized_grant_types  VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities             VARCHAR(256),
  access_token_validity   INTEGER,
  refresh_token_validity  INTEGER,
  additional_information  VARCHAR(4096),
  autoapprove             VARCHAR(256)
);

INSERT INTO oauth_client_details
(
  client_id,
  resource_ids,
  client_secret,
  scope,
  authorized_grant_types,
  web_server_redirect_uri,
  authorities,
  access_token_validity,
  refresh_token_validity,
  additional_information,
  autoapprove
)
  SELECT
    'clientapp',
    'restservice',
    '$2a$13$m3lV588ApKfBP9LN3YJ7HOfWJ7g2Qg630IJN1jvPhdHju7bSBjwtS',
    'read,write',
    'authorization_code,refresh_token,password',
    NULL,
    'USER',
    NULL,
    NULL,
    NULL,
    NULL
  WHERE NOT EXISTS(
      SELECT *
      FROM oauth_client_details
  );

CREATE TABLE IF NOT EXISTS oauth_access_token (
  token_id          VARCHAR(256),
  token             BYTEA,
  authentication_id VARCHAR(256),
  user_name         VARCHAR(256),
  client_id         VARCHAR(256),
  authentication    BYTEA,
  refresh_token     VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS oauth_refresh_token (
  token_id       VARCHAR(256),
  token          BYTEA,
  authentication BYTEA
);

CREATE TABLE IF NOT EXISTS  acl_class (
  id    BIGSERIAL    NOT NULL PRIMARY KEY,
  class VARCHAR(100) NOT NULL,
  CONSTRAINT unique_uk_2 UNIQUE (class)
);

CREATE TABLE IF NOT EXISTS  acl_object_identity (
  id                 BIGSERIAL PRIMARY KEY,
  object_id_class    BIGINT  NOT NULL,
  object_id_identity BIGINT  NOT NULL,
  parent_object      BIGINT,
  owner_sid          BIGINT,
  entries_inheriting BOOLEAN NOT NULL,
  CONSTRAINT unique_uk_3 UNIQUE (object_id_class, object_id_identity),
  CONSTRAINT foreign_fk_1 FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id),
  CONSTRAINT foreign_fk_2 FOREIGN KEY (object_id_class) REFERENCES acl_class (id),
  CONSTRAINT foreign_fk_3 FOREIGN KEY (owner_sid) REFERENCES app_user (user_id)
);

CREATE TABLE IF NOT EXISTS  acl_entry (
  id                  BIGSERIAL PRIMARY KEY,
  acl_object_identity BIGINT  NOT NULL,
  ace_order           INT     NOT NULL,
  sid                 BIGINT  NOT NULL,
  mask                INTEGER NOT NULL,
  granting            BOOLEAN NOT NULL,
  audit_success       BOOLEAN NOT NULL,
  audit_failure       BOOLEAN NOT NULL,
  CONSTRAINT unique_uk_4 UNIQUE (acl_object_identity, ace_order),
  CONSTRAINT foreign_fk_4 FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id),
  CONSTRAINT foreign_fk_5 FOREIGN KEY (sid) REFERENCES app_user (user_id)
);

INSERT INTO  acl_class (id, class) VALUES
  (1, 'com.ua.oauth.domain.Admin'),
  (2, 'com.ua.oauth.domain.Personal'),
  (3, 'com.ua.oauth.domain.Public');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
VALUES
  (1, 1, 1, NULL, 1, FALSE),
  (2, 1, 2, NULL, 1, FALSE),
  (3, 1, 3, NULL, 1, FALSE),
  (4, 2, 1, NULL, 1, FALSE),
  (5, 2, 2, NULL, 1, FALSE),
  (6, 2, 3, NULL, 1, FALSE),
  (7, 3, 1, NULL, 1, FALSE),
  (8, 3, 2, NULL, 1, FALSE),
  (9, 3, 3, NULL, 1, FALSE);

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
  (1, 1, 1, 1, 1, TRUE, TRUE, TRUE),
  (2, 2, 1, 1, 1, TRUE, TRUE, TRUE),
  (3, 3, 1, 1, 1, TRUE, TRUE, TRUE),
  (4, 1, 2, 1, 2, TRUE, TRUE, TRUE),
  (5, 2, 2, 1, 2, TRUE, TRUE, TRUE),
  (6, 3, 2, 1, 2, TRUE, TRUE, TRUE),
  (7, 4, 1, 1, 1, TRUE, TRUE, TRUE),
  (8, 5, 1, 1, 1, TRUE, TRUE, TRUE),
  (9, 6, 1, 1, 1, TRUE, TRUE, TRUE),
  (10, 7, 1, 1, 1, TRUE, TRUE, TRUE),
  (11, 8, 1, 1, 1, TRUE, TRUE, TRUE),
  (12, 9, 1, 1, 1, TRUE, TRUE, TRUE),
  (13, 7, 2, 1, 2, TRUE, TRUE, TRUE),
  (14, 8, 2, 1, 2, TRUE, TRUE, TRUE),
  (15, 9, 2, 1, 2, TRUE, TRUE, TRUE);