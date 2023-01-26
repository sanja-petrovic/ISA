CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS public.role
(
    id uuid NOT NULL,
    version integer,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT role_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.users
(
    id uuid NOT NULL,
    version integer,
    email character varying(255) COLLATE pg_catalog."default",
    first_name character varying(255) COLLATE pg_catalog."default",
    gender character varying(255) COLLATE pg_catalog."default",
    is_verified boolean,
    last_name character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    personal_id character varying(255) COLLATE pg_catalog."default",
    phone_number character varying(255) COLLATE pg_catalog."default",
    role_id uuid,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
    CONSTRAINT uk_9q63snka3mdh91as4io72espi UNIQUE (phone_number),
    CONSTRAINT uk_luap7jdaw7hribnm5woandmcf UNIQUE (personal_id),
    CONSTRAINT fk4qu1gr772nnf6ve5af002rwya FOREIGN KEY (role_id)
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS public.refresh_tokens
(
    token character varying(255) COLLATE pg_catalog."default" NOT NULL,
    expiry_date bigint NOT NULL,
    user_id uuid,
    CONSTRAINT refresh_tokens_pkey PRIMARY KEY (token)
);


CREATE TABLE IF NOT EXISTS public.blood_banks
(
    id uuid NOT NULL,
    version integer,
    city character varying(255) COLLATE pg_catalog."default",
    country character varying(255) COLLATE pg_catalog."default",
    street character varying(255) COLLATE pg_catalog."default",
    average_grade double precision,
    description character varying(255) COLLATE pg_catalog."default",
    title character varying(255) COLLATE pg_catalog."default",
    interval_end time without time zone,
    interval_start time without time zone,
    latitude double precision,
    longitude double precision,
    CONSTRAINT blood_banks_pkey PRIMARY KEY (id),
    CONSTRAINT uk_nmki042g2u18p2a8povcb6r91 UNIQUE (title)
);

CREATE TABLE IF NOT EXISTS public.admins
(
    id uuid NOT NULL,
    version integer,
    email character varying(255) COLLATE pg_catalog."default",
    first_name character varying(255) COLLATE pg_catalog."default",
    gender character varying(255) COLLATE pg_catalog."default",
    is_verified boolean,
    last_name character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    personal_id character varying(255) COLLATE pg_catalog."default",
    phone_number character varying(255) COLLATE pg_catalog."default",
    role_id uuid,
    first_password character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT admins_pkey PRIMARY KEY (id),
    CONSTRAINT uk_47bvqemyk6vlm0w7crc3opdd4 UNIQUE (email),
    CONSTRAINT uk_jld98mhubn4q39ac763tdb8oh UNIQUE (phone_number),
    CONSTRAINT uk_r4gl0vhxba8cfgj5wbe1yr2hm UNIQUE (personal_id),
    CONSTRAINT fk_721om36osan088rncmuxn6sal FOREIGN KEY (role_id)
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS public.blood_donors
(
    id uuid NOT NULL,
    version integer,
    email character varying(255) COLLATE pg_catalog."default",
    first_name character varying(255) COLLATE pg_catalog."default",
    gender character varying(255) COLLATE pg_catalog."default",
    is_verified boolean,
    last_name character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    personal_id character varying(255) COLLATE pg_catalog."default",
    phone_number character varying(255) COLLATE pg_catalog."default",
    role_id uuid,
    city character varying(255) COLLATE pg_catalog."default",
    country character varying(255) COLLATE pg_catalog."default",
    street character varying(255) COLLATE pg_catalog."default",
    institution character varying(255) COLLATE pg_catalog."default",
    loyalty_status character varying(255) COLLATE pg_catalog."default",
    occupation character varying(255) COLLATE pg_catalog."default",
    penalty_count integer,
    CONSTRAINT blood_donors_pkey PRIMARY KEY (id),
    CONSTRAINT uk_9f28i1pqnpgw0inh0m2qmtmvl UNIQUE (personal_id),
    CONSTRAINT uk_bc2fnchn92427rg6nwln77bl0 UNIQUE (phone_number),
    CONSTRAINT uk_cne9e5jvrnk5mol7sa50rugjq UNIQUE (email),
    CONSTRAINT fk_k75f7u9bi0luj8xn1kx8mfjwg FOREIGN KEY (role_id)
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS public.medical_staff
(
    id uuid NOT NULL,
    version integer,
    email character varying(255) COLLATE pg_catalog."default",
    first_name character varying(255) COLLATE pg_catalog."default",
    gender character varying(255) COLLATE pg_catalog."default",
    is_verified boolean,
    last_name character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    personal_id character varying(255) COLLATE pg_catalog."default",
    phone_number character varying(255) COLLATE pg_catalog."default",
    role_id uuid,
    blood_bank_id uuid NOT NULL,
    CONSTRAINT medical_staff_pkey PRIMARY KEY (id),
    CONSTRAINT uk_7wxj98s6o2yfr3ywgo72y1c90 UNIQUE (personal_id),
    CONSTRAINT uk_d8069nrd9qk75hlbx1e3d4wm1 UNIQUE (email),
    CONSTRAINT uk_plg6s69ocn1nnbdbsvmfwxcxb UNIQUE (phone_number),
    CONSTRAINT fk_cdew03d1nbidftjvem7txr2vo FOREIGN KEY (role_id)
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkim25pcmp591v5f79wjec5gimt FOREIGN KEY (blood_bank_id)
        REFERENCES public.blood_banks (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS public.news
(
    id uuid NOT NULL,
    version integer,
    body character varying(255) COLLATE pg_catalog."default",
    milliseconds bigint,
    title character varying(255) COLLATE pg_catalog."default",
    blood_bank_id uuid,
    CONSTRAINT news_pkey PRIMARY KEY (id),
    CONSTRAINT fk4xb6vo7cbq5ksqnxqq9uwiny1 FOREIGN KEY (blood_bank_id)
        REFERENCES public.blood_banks (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS public.questions
(
    id uuid NOT NULL,
    version integer,
    text character varying(255) COLLATE pg_catalog."default",
    type character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT questions_pkey PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS public.answers
(
    id uuid NOT NULL,
    version integer,
    value boolean,
    blood_donor_id uuid,
    question_id uuid,
    CONSTRAINT answers_pkey PRIMARY KEY (id),
    CONSTRAINT fk3erw1a3t0r78st8ty27x6v3g1 FOREIGN KEY (question_id)
        REFERENCES public.questions (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk3ykj3jolbga2kiif7dd9skgsg FOREIGN KEY (blood_donor_id)
        REFERENCES public.blood_donors (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS public.blood_requests
(
    id uuid NOT NULL,
    version integer,
    amount double precision,
    blood_type integer,
    received_date timestamp without time zone,
    send_on_date timestamp without time zone,
    status character varying(255) COLLATE pg_catalog."default",
    urgent boolean,
    blood_bank_id uuid,
    CONSTRAINT blood_requests_pkey PRIMARY KEY (id),
    CONSTRAINT fk79xwrlaiwiaa7e9nagmo9t9y9 FOREIGN KEY (blood_bank_id)
        REFERENCES public.blood_banks (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS public.blood_subscriptions
(
    id uuid NOT NULL,
    version integer,
    active boolean,
    amount double precision,
    delivery_date integer,
    origin_id character varying(255) COLLATE pg_catalog."default",
    type character varying(255) COLLATE pg_catalog."default",
    blood_bank_id uuid,
    CONSTRAINT blood_subscriptions_pkey PRIMARY KEY (id),
    CONSTRAINT ukr7arb12mg0cxqexh2naqb6gcc UNIQUE (blood_bank_id, type),
    CONSTRAINT fktqlq9ffciibxd8bemhh62bvc8 FOREIGN KEY (blood_bank_id)
        REFERENCES public.blood_banks (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS public.blood_supplies
(
    id uuid NOT NULL,
    version integer,
    amount double precision,
    type character varying(255) COLLATE pg_catalog."default",
    blood_bank_id uuid,
    CONSTRAINT blood_supplies_pkey PRIMARY KEY (id),
    CONSTRAINT uknk6uj221t6s43915dpck6nep9 UNIQUE (type, blood_bank_id),
    CONSTRAINT fk6p82rimviiap8um8bs610qu7l FOREIGN KEY (blood_bank_id)
        REFERENCES public.blood_banks (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS public.appointments
(
    id uuid NOT NULL,
    version integer,
    date_time timestamp without time zone,
    duration bigint,
    status character varying(255),
    blood_bank_id uuid NOT NULL,
    blood_donor_id uuid,
    report text
) PARTITION BY LIST(status);
ALTER TABLE public.appointments
    ADD CONSTRAINT pk_appointments PRIMARY KEY (id, status);
ALTER TABLE public.appointments
    ADD CONSTRAINT fk_blood_donors FOREIGN KEY (blood_donor_id) REFERENCES public.blood_donors (id);
ALTER TABLE public.appointments
    ADD CONSTRAINT fk_blood_banks FOREIGN KEY (blood_bank_id) REFERENCES public.blood_banks (id);
CREATE TABLE appointments_future PARTITION OF appointments FOR VALUES IN ('SCHEDULED', 'NOT_SCHEDULED');
CREATE TABLE appointments_past PARTITION OF appointments FOR VALUES IN ('COMPLETED', 'CANCELLED', 'MISSED', 'NOT_HELD', 'DENIED');


CREATE TABLE IF NOT EXISTS public.reviews
(
    id uuid NOT NULL,
    version integer,
    content character varying(255) COLLATE pg_catalog."default",
    blood_bank_id uuid NOT NULL,
    reviewer_id uuid NOT NULL,
    CONSTRAINT reviews_pkey PRIMARY KEY (id),
    CONSTRAINT fkdw5imolhl5ot2uk2f1msth842 FOREIGN KEY (blood_bank_id)
        REFERENCES public.blood_banks (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkpglg1vjas26ao2j1ib2fj0cwu FOREIGN KEY (reviewer_id)
        REFERENCES public.blood_donors (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS public.supplies
(
    id uuid NOT NULL,
    version integer,
    amount integer,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT supplies_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.tracking_requests
(
    id uuid NOT NULL,
    end_latitude double precision,
    end_longitude double precision,
    start_latitude double precision,
    start_longitude double precision,
    "timestamp" timestamp without time zone,
    frequency_in_seconds integer,
    blood_bank_id uuid,
    status character varying(255),
    CONSTRAINT tracking_requests_pkey PRIMARY KEY (id),
    CONSTRAINT fkq05i77gej2y8gfke025e7tuig FOREIGN KEY (blood_bank_id)
        REFERENCES public.blood_banks (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


-- blood banks
insert into public.blood_banks (id, city, country, street, average_grade, description, title, interval_end,
                                interval_start, latitude, longitude)
values ('16e4a8c2-3e86-4e93-825f-24e36cb29669', 'Novi Sad', 'Srbija', 'Danila Kiša 15', 4.32, 'Bankica',
        'Moja banka krvi', '22:00:00', '10:00:00', 45.24836608833101, 19.83678175453625);

insert into public.blood_banks (id, city, country, street,  average_grade, description, title, interval_end,
                                interval_start, latitude, longitude)
values (uuid_generate_v4(), 'Belgrade', 'Srbija', 'Ljube Jovanovića 12', 4.11, 'Najveća banka krvi u Beogradu',
        'Institut za transfuziju krvi', '24:00:00', '00:00:00', 44.799650134139064, 20.468411795043);

insert into public.blood_banks (id, city, country, street,  average_grade, description, title, interval_end,
                                interval_start, latitude, longitude)
values (uuid_generate_v4(), 'Zrenjanin', 'Srbija', 'Sremska 1', 3.17, 'Zrenjaninska banka krvi', 'Plasma Point',
        '20:00:00', '08:00:00', 45.377631105541056, 20.415080939202895);

insert into public.blood_banks (id, city, country, street, average_grade, description, title, interval_end,
                                interval_start, latitude, longitude)
values (uuid_generate_v4(), 'Novi Sad', 'Srbija', 'Hajduk Veljkova 7', 4.66, 'Najveća banka krvi u Novom Sadu',
        'Zavod za transfuziju krvi Vojvodine', '24:00:00', '00:00:00', 45.25378411698129, 19.824104893176354);

insert into public.blood_banks (id, city, country, street, average_grade, description, title, interval_end,
                                interval_start, latitude, longitude)
values (uuid_generate_v4(), 'Belgrade', 'Srbija', 'Šekspirova 25', 3.91, '--',
        'Crveni krst', '22:00:00', '10:00:00', 44.78282950620948, 20.460097798687478);

insert into public.blood_banks (id, city, country, street, average_grade, description, title, interval_end,
                                interval_start, latitude, longitude)
values (uuid_generate_v4(), 'Subotica', 'Srbija', 'Ive Lole Ribara 20', 4.68, '--',
        'Srce', '19:00:00', '07:00:00', 46.096383598764966, 19.665005154580147);

insert into public.blood_banks (id, city, country, street, average_grade, description, title, interval_end,
                                interval_start, latitude, longitude)
values (uuid_generate_v4(), 'Niš', 'Srbija', 'Dušanova 39', 4.25, '--',
        'Centar krvi', '19:00:00', '07:00:00', 43.317637651623485, 21.89863772386244);

insert into public.blood_banks (id, city,  country, street, average_grade, description, title, interval_end,
                                interval_start, latitude, longitude)
values (uuid_generate_v4(), 'Niš', 'Srbija', 'Zetska 2', 4.94, '--',
        'Zavod "Milenko Hadžić"', '20:00:00', '08:00:00', 43.31767170857, 21.91097694094361);

-- questions
INSERT INTO public.questions(id, text, type)
VALUES ('60cfe366-8a12-453f-ae6c-78a22f90dbb5', 'Have you ever donated blood or blood components before?', 'FOR_ALL');

INSERT INTO public.questions(id, text, type)
VALUES ('b1a2aa65-c15f-46c6-b088-4336ae8ed265',
        'Have you ever before been rejected from donating blood or blood components?', 'FOR_ALL');

INSERT INTO public.questions(id, text, type)
VALUES ('0ecd3dc2-6d98-498e-99b0-e8072bcb3a3e',
        'Do you currently feel healthy and rested to donate blood or blood components?', 'FOR_ALL');

INSERT INTO public.questions(id, text, type)
VALUES ('a565f190-9270-4ac1-840f-f08c252ca609',
        'Have you eaten before your blood or blood component donation appointment?', 'FOR_ALL');

INSERT INTO public.questions(id, text, type)
VALUES ('53e34347-d0d9-43bb-9650-d0901132e0d3', 'Do you take any medication on a daily basis?', 'FOR_ALL');

INSERT INTO public.questions(id, text, type)
VALUES ('3ef25d2e-9e39-4438-a6de-d311dc4718fc', 'Have you taken any medication in the past 2-3 days?', 'FOR_ALL');

INSERT INTO public.questions(id, text, type)
VALUES ('a88dceb9-8eb8-4f04-befa-e0fd016b4a37',
        'Do you take Aspirin (Cardiopirin) often? Have you taken it in the past 5 days?', 'FOR_ALL');

INSERT INTO public.questions(id, text, type)
VALUES ('a48374fb-a3dc-41c1-a5f2-c33e89a3376e',
        'Have you ever been treated in a hospital, or are currently being treated in a hospital?', 'FOR_ALL');

INSERT INTO public.questions(id, text, type)
VALUES ('a695d651-51ce-480b-9c7c-6e2740bb5407', 'Have you had a tooth extraction in the past 7 days?', 'FOR_ALL');

INSERT INTO public.questions(id, text, type)
VALUES ('43585871-4fbb-429c-9961-1c13512e9824',
        'Have you had a fever over 38°C, a runny nose, cold or taken antibiotics in the past 7-10 days?', 'FOR_ALL');

INSERT INTO public.questions(id, text, type)
VALUES ('ee6f8eae-9a46-4679-a876-a5fbe9db0851', 'Do you weigh over 50 kilograms?',
        'FOR_ALL');

INSERT INTO public.questions(id, text, type)
VALUES ('7ad4907c-5561-4e4b-8255-6979f347cfc7',
        'Do you suffer from chronic hypertension (high blood pressure) or hypertension (low blood pressure)?', 'FOR_ALL');

INSERT INTO public.questions(id, text, type)
VALUES ('fb5b732e-5f7f-46f9-a9e1-c6d76a2c2ccf', 'Do you have any skin changes or suffer from allergies?', 'FOR_ALL');

INSERT INTO public.questions(id, text, type)
VALUES ('47079b6c-6ee5-4d5d-8cec-00723e2fb513', 'Have you in the past 6 months had any surgery or received blood?',
        'FOR_ALL');

INSERT INTO public.questions(id, text, type)
VALUES ('5bc7ceab-e697-467f-86aa-39c475e27926',
        'Have you in the past 6 months had acupuncture done, a piercing or a tattoo?', 'FOR_ALL');

INSERT INTO public.questions(id, text, type)
VALUES ('5e45116f-b85d-4a49-af3b-79746cfe28f5', 'Are you currently pregnant?', 'FOR_WOMEN');

INSERT INTO public.questions(id, text, type)
VALUES ('9274a7eb-8c9e-4d50-badc-debd665f34b1', 'Are you currently on your period?', 'FOR_WOMEN');

--blood supplies
insert into public.blood_supplies(id, amount, type, blood_bank_id)
values (uuid_generate_v4(),360,'A_POSITIVE', '16e4a8c2-3e86-4e93-825f-24e36cb29669');
insert into public.blood_supplies(id,amount, type, blood_bank_id)
values (uuid_generate_v4(),150,'B_POSITIVE', '16e4a8c2-3e86-4e93-825f-24e36cb29669');
insert into public.blood_supplies(id,amount, type, blood_bank_id)
values (uuid_generate_v4(),240,'O_POSITIVE', '16e4a8c2-3e86-4e93-825f-24e36cb29669');
insert into public.blood_supplies(id,amount, type, blood_bank_id)
values (uuid_generate_v4(),30,'AB_POSITIVE', '16e4a8c2-3e86-4e93-825f-24e36cb29669');
insert into public.blood_supplies(id,amount, type, blood_bank_id)
values (uuid_generate_v4(),120,'A_NEGATIVE', '16e4a8c2-3e86-4e93-825f-24e36cb29669');
insert into public.blood_supplies(id,amount, type, blood_bank_id)
values (uuid_generate_v4(),210,'B_NEGATIVE', '16e4a8c2-3e86-4e93-825f-24e36cb29669');
insert into public.blood_supplies(id,amount, type, blood_bank_id)
values (uuid_generate_v4(),20,'O_NEGATIVE', '16e4a8c2-3e86-4e93-825f-24e36cb29669');
insert into public.blood_supplies(id,amount, type, blood_bank_id)
values (uuid_generate_v4(),350,'AB_NEGATIVE', '16e4a8c2-3e86-4e93-825f-24e36cb29669');

-- blood requests
INSERT INTO public.blood_requests(
    id, version, amount, blood_type, received_date, send_on_date, status, urgent, blood_bank_id)
VALUES (uuid_generate_v4(), 0, 5, 0, '2023-01-23', null, 'APPROVED', false, '16e4a8c2-3e86-4e93-825f-24e36cb29669');
INSERT INTO public.blood_requests(
    id, version, amount, blood_type, received_date, send_on_date, status, urgent, blood_bank_id)
VALUES (uuid_generate_v4(), 0, 10, 0, '2023-01-22', null, 'APPROVED', false, '16e4a8c2-3e86-4e93-825f-24e36cb29669');

-- appointments - run in pgAdmin after setup
-- insert into public.appointments(id,date_time,duration,status,blood_bank_id,blood_donor_id) values (uuid_generate_v4(),'2023-02-02 11:30:00',30,'SCHEDULED','16e4a8c2-3e86-4e93-825f-24e36cb29669','16e4a8c2-3e86-4e93-825f-24e36cb29645');
-- insert into public.appointments(id,date_time,duration,status,blood_bank_id,blood_donor_id) values (uuid_generate_v4(),'2023-02-02 9:30:00',15,'NOT_SCHEDULED','16e4a8c2-3e86-4e93-825f-24e36cb29669',null);
-- insert into public.appointments(id,date_time,duration,status,blood_bank_id,blood_donor_id) values (uuid_generate_v4(),'2023-02-02 18:00:00',30,'NOT_SCHEDULED','16e4a8c2-3e86-4e93-825f-24e36cb29669',null);
-- insert into public.appointments(id,date_time,duration,status,blood_bank_id,blood_donor_id) values (uuid_generate_v4(),'2023-02-05 12:00:00',30,'NOT_SCHEDULED','16e4a8c2-3e86-4e93-825f-24e36cb29669', null);
-- insert into public.appointments(id,date_time,duration,status,blood_bank_id,blood_donor_id) values (uuid_generate_v4(),'2023-03-30 18:00:00',30,'SCHEDULED','16e4a8c2-3e86-4e93-825f-24e36cb29669', '16e4a8c2-3e86-4e93-825f-24e36cb29645');
-- insert into public.appointments(id,date_time,duration,status,blood_bank_id,blood_donor_id) values (uuid_generate_v4(),'2022-02-12 18:00:00',30,'MISSED','16e4a8c2-3e86-4e93-825f-24e36cb29669', '16e4a8c2-3e86-4e93-825f-24e36cb29645');
