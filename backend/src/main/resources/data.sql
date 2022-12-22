CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- blood banks
insert into public.blood_banks (id, city, street, country, average_grade, description, title, interval_end,
                                interval_start)
values ('16e4a8c2-3e86-4e93-825f-24e36cb29669', 'Novi Sad', 'Srbija', 'Danila Kiša 15', 4.32, 'Bankica', 'Moja banka krvi', '22:00:00',
        '10:00:00');
insert into public.blood_banks (id, city, street, country, average_grade, description, title, interval_end,
                                interval_start)
values (uuid_generate_v4(), 'Belgrade', 'Srbija', 'Ljube Jovanovića 12', 4.11, 'Najveća banka krvi u Beogradu',
        'Institut za transfuziju krvi', '24:00:00', '00:00:00');
insert into public.blood_banks (id, city, street, country, average_grade, description, title, interval_end,
                                interval_start)
values (uuid_generate_v4(), 'Zrenjanin', 'Srbija', 'Sremska 1', 3.17, 'Zrenjaninska banka krvi', 'Plasma Point',
        '20:00:00', '08:00:00');
insert into public.blood_banks (id, city, street, country, average_grade, description, title, interval_end,
                                interval_start)
values (uuid_generate_v4(), 'Novi Sad', 'Srbija', 'Hajduk Veljkova 7', 4.66, 'Najveća banka krvi u Novom Sadu',
        'Zavod za transfuziju krvi Vojvodine', '24:00:00', '00:00:00');
insert into public.blood_banks (id, city, street, country, average_grade, description, title, interval_end,
                                interval_start)
values (uuid_generate_v4(), 'Belgrade', 'Srbija', 'Šekspirova 25', 3.91, '--', 'Crveni krst', '22:00:00', '10:00:00');
insert into public.blood_banks (id, city, street, country, average_grade, description, title, interval_end,
                                interval_start)
values (uuid_generate_v4(), 'Subotica', 'Srbija', 'Ive Lole Ribara 20', 4.68, '--', 'Srce', '19:00:00', '07:00:00');
insert into public.blood_banks (id, city, street, country, average_grade, description, title, interval_end,
                                interval_start)
values (uuid_generate_v4(), 'Niš', 'Srbija', 'Dušanova 39', 4.25, '--', 'Centar krvi', '19:00:00', '07:00:00');
insert into public.blood_banks (id, city, street, country, average_grade, description, title, interval_end,
                                interval_start)
values (uuid_generate_v4(), 'Niš', 'Srbija', 'Zetska 2', 4.94, '--', 'Zavod "Milenko Hadžić"', '20:00:00', '08:00:00');

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
VALUES ('cb97daa9-2969-42ed-a64a-79f6fd8dfc9e', 'Do you have a dangerous career or hobbies?', 'FOR_ALL');
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
VALUES ('1fb576d3-0d92-4968-99bb-bfd522b1ab9a', 'Have you gotten any vaccines or serums in the past 12 months?',
        'FOR_ALL');
INSERT INTO public.questions(id, text, type)
VALUES ('ee6f8eae-9a46-4679-a876-a5fbe9db0851', 'Have you gone through sudden weight loss in the past 6 months?',
        'FOR_ALL');
INSERT INTO public.questions(id, text, type)
VALUES ('7ad4907c-5561-4e4b-8255-6979f347cfc7',
        'Have you been bitten by a tick in the past 12 months, and did you see a doctor about it?', 'FOR_ALL');
INSERT INTO public.questions(id, text, type)
VALUES ('7b05412b-6f73-42be-96c5-9a28226e8e41',
        'Have you ever been treated for epilepsy, diabetes, asthma, tuberculosis, heart attack, stroke, malignant diseases, mental illness or malaria?',
        'FOR_ALL');
INSERT INTO public.questions(id, text, type)
VALUES ('d9bc821b-6b12-4443-964c-87bffe5b8716',
        'Do you suffer from some other chronic illness of: the heart, lungs, kidneys, liver, stomach, intestine, bone, nervous system, blood or blood vessels?',
        'FOR_ALL');
INSERT INTO public.questions(id, text, type)
VALUES ('734b11b9-8f6a-473a-a433-ccf08ac2e160',
        'Have you ever had issues with the thyroid gland, pituitary gland, and/or taken hormones?', 'FOR_ALL');
INSERT INTO public.questions(id, text, type)
VALUES ('fb5b732e-5f7f-46f9-a9e1-c6d76a2c2ccf', 'Do you have any skin changes or suffer from allergies?', 'FOR_ALL');
INSERT INTO public.questions(id, text, type)
VALUES ('264e811d-2f63-4ecc-bdf9-6741078664d4',
        'Do you bleed for a long time after an injury, or spontaneously get bruises?', 'FOR_ALL');
INSERT INTO public.questions(id, text, type)
VALUES ('47079b6c-6ee5-4d5d-8cec-00723e2fb513', 'Have you in the past 6 months had any surgery or received blood?',
        'FOR_ALL');
INSERT INTO public.questions(id, text, type)
VALUES ('e4e6c7b5-cb37-4c92-8b05-bee99a2e602f', 'Have you in the past 6 months traveled abroad or lived abroad?',
        'FOR_ALL');
INSERT INTO public.questions(id, text, type)
VALUES ('5bc7ceab-e697-467f-86aa-39c475e27926',
        'Have you in the past 6 months had acupuncture done, a piercing or a tattoo?', 'FOR_ALL');
INSERT INTO public.questions(id, text, type)
VALUES ('d5841476-ccd3-4d12-8190-2183d7537b9d', 'Have you drank alcohol in the past 6 hours?', 'FOR_ALL');
INSERT INTO public.questions(id, text, type)
VALUES ('36d83f88-9e0c-4b71-9680-a3c979b87918', 'Have you ever had hepatitis A, B or C?', 'FOR_ALL');
INSERT INTO public.questions(id, text, type)
VALUES ('3d071331-1573-428f-bb8b-9de3ba1f8865',
        'Have you ever been in contact with someone who has hepatitis A, B or C?', 'FOR_ALL');
INSERT INTO public.questions(id, text, type)
VALUES ('a4b79885-5ffe-42a3-add0-c7573a170210', 'Do you think there was ever a possibility to contract HIV?',
        'FOR_ALL');
INSERT INTO public.questions(id, text, type)
VALUES ('a8d7c2c5-3c0a-4edb-ac95-4558ee62e92a', 'Have you ever done any kind of drugs?', 'FOR_ALL');
INSERT INTO public.questions(id, text, type)
VALUES ('9e18c761-dd7d-442d-8ebc-77d7e1cb464d', 'Have you ever taken steroids or illegal medicine?', 'FOR_ALL');
INSERT INTO public.questions(id, text, type)
VALUES ('5e45116f-b85d-4a49-af3b-79746cfe28f5', 'Are you currently pregnant?', 'FOR_WOMEN');
INSERT INTO public.questions(id, text, type)
VALUES ('9274a7eb-8c9e-4d50-badc-debd665f34b1', 'Are you currently on your period?', 'FOR_WOMEN');
INSERT INTO public.questions(id, text, type)
VALUES ('99cc9319-f99d-466a-b2e7-f589ec69f83d',
        'Have you in the past 6 months given birth or had a pregnancy termination?', 'FOR_WOMEN');

--appointments
insert into public.appointments(id,date_time,duration,status,blood_bank_id,blood_donor_id)
values (uuid_generate_v4(),'2023-02-02 11:30:00',30,'SCHEDULED','16e4a8c2-3e86-4e93-825f-24e36cb29655','16e4a8c2-3e86-4e93-825f-24e36cb29645');
insert into public.appointments(id,date_time,duration,status,blood_bank_id,blood_donor_id)
values (uuid_generate_v4(),'2023-02-02 9:30:00',15,'NOT_SCHEDULED','16e4a8c2-3e86-4e93-825f-24e36cb29669',null);
insert into public.appointments(id,date_time,duration,status,blood_bank_id,blood_donor_id)
values (uuid_generate_v4(),'2023-02-02 18:00:00',30,'NOT_SCHEDULED','16e4a8c2-3e86-4e93-825f-24e36cb29669',null);
insert into public.appointments(id,date_time,duration,status,blood_bank_id,blood_donor_id)
values (uuid_generate_v4(),'2023-02-05 12:00:00',30,'NOT_SCHEDULED','16e4a8c2-3e86-4e93-825f-24e36cb29669', null);
insert into public.appointments(id,date_time,duration,status,blood_bank_id,blood_donor_id)
values (uuid_generate_v4(),'2022-12-30 18:00:00',30,'SCHEDULED','16e4a8c2-3e86-4e93-825f-24e36cb29655', '16e4a8c2-3e86-4e93-825f-24e36cb29645');
insert into public.appointments(id,date_time,duration,status,blood_bank_id,blood_donor_id)
values (uuid_generate_v4(),'2023-01-12 18:00:00',30,'SCHEDULED','16e4a8c2-3e86-4e93-825f-24e36cb29655', '16e4a8c2-3e86-4e93-825f-24e36cb29645');



--supplies
insert into public.supplies(id,amount, name)
values (uuid_generate_v4(),300,'Igla');
insert into public.supplies(id,amount, name)
values (uuid_generate_v4(),200,'Spric');
insert into public.supplies(id,amount, name)
values (uuid_generate_v4(),100,'Epruveta');
insert into public.supplies(id,amount, name)
values (uuid_generate_v4(),400,'Gaza');
insert into public.supplies(id,amount, name)
values (uuid_generate_v4(),50,'Hanzaplast');

--blood supplies
insert into public.blood_supplies(id,amount, type, blood_bank_id)
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
