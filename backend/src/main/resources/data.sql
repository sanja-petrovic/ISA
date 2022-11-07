CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

TRUNCATE  public.patients;

INSERT INTO public.patients (
id, email, first_name, gender, last_name, password, personal_id, phone_number, city, country, "number", street, institution_info, occupation, account_status, is_verified) VALUES (
uuid_generate_v4()::uuid, 'gugma@gugma.rs'::character varying, 'Gugma'::character varying, 'MALE'::character varying, 'Gugmic'::character varying, '1324'::character varying, 'supersus1'::character varying, '0612406374'::character varying, 'New Now'::character varying, 'Birbia'::character varying, '8'::character varying, 'Markova'::character varying, 'Info stvarno'::character varying, 'Zaposlen'::character varying, 'VERIFIED'::character varying, true::boolean)
 returning id;