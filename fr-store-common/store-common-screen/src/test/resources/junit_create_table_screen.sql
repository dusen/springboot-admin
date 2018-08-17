Drop Table IF EXISTS m_screen;
CREATE TABLE m_screen(
 screen_name varchar(100) NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30) DEFAULT 'fr-store-common-screen',
 update_user_id varchar(30),
 update_datetime timestamptz NOT NULL,
 update_program_id varchar(30) DEFAULT 'fr-store-common-screen',
  CONSTRAINT m_screen_pk
   PRIMARY KEY(screen_name)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;

Drop Table IF EXISTS m_menu_screen;
CREATE TABLE m_menu_screen(
 menu_name varchar(100) NOT NULL,
 screen_name varchar(100) NOT NULL,
 display_order numeric(2) NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30) DEFAULT 'fr-store-common-screen',
 update_user_id varchar(30),
 update_datetime timestamptz NOT NULL,
 update_program_id varchar(30) DEFAULT 'fr-store-common-screen',
  CONSTRAINT m_menu_screen_pk
   PRIMARY KEY(menu_name,screen_name)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;

Drop Table IF EXISTS m_role_accessible_screen;
CREATE TABLE m_role_accessible_screen(
 role varchar(100) NOT NULL,
 screen_name varchar(100) NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30) DEFAULT 'fr-store-common-screen',
 update_user_id varchar(30),
 update_datetime timestamptz NOT NULL,
 update_program_id varchar(30) DEFAULT 'fr-store-common-screen',
  CONSTRAINT m_role_accessible_screen_pk
   PRIMARY KEY(role,screen_name)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;

Drop Table IF EXISTS m_screen_path;
CREATE TABLE m_screen_path(
 screen_name varchar(100) NOT NULL,
 path varchar(300) NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30) DEFAULT 'fr-store-common-screen',
 update_user_id varchar(30),
 update_datetime timestamptz NOT NULL,
 update_program_id varchar(30) DEFAULT 'fr-store-common-screen',
  CONSTRAINT m_screen_path_pk
   PRIMARY KEY(screen_name,path)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;
