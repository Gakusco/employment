INSERT INTO roles (id, `role`) VALUES (1, "ROLE_SUPERVISOR"), (2, "ROLE_POSTULANT");
INSERT INTO credentials (id, email, password) VALUES (1, "nadie@nadie.com", "12345"), (2, "nada@nada.com", "12345");
INSERT INTO credencial_roles (id_credencial, id_role) VALUES (1, 1), (2, 2);

INSERT INTO users (id, name, last_name) VALUES (1, "max", "nadie");
INSERT INTO applicants (id, web, phone_number, email, run, date_of_birth, curriculum_vitae) VALUES (1, "www.asdfasdf.com", "+56934231234", "dummy@dummy.com", "19.234.123-2", "2000-02-20", "nada.pdf");
INSERT INTO users (id, name, last_name) VALUES (2, "estel", "nadie");
INSERT INTO applicants (id, web, phone_number, email, run, date_of_birth, curriculum_vitae) VALUES (2, "www.asdfasdf.com", "+56934231234", "dummy@dummy.com", "19.234.123-2", "2000-02-20", "nada.pdf");
INSERT INTO users (id, name, last_name) VALUES (3, "nat", "nadie");
INSERT INTO applicants (id, web, phone_number, email, run, date_of_birth, curriculum_vitae) VALUES (3, "www.asdfasdf.com", "+56934231234", "dummy@dummy.com", "19.234.123-2", "2000-02-20", "nada.pdf");
INSERT INTO users (id, name, last_name) VALUES (4, "ele", "nadie");
INSERT INTO applicants (id, web, phone_number, email, run, date_of_birth, curriculum_vitae) VALUES (4, "www.asdfasdf.com", "+56934231234", "dummy@dummy.com", "19.234.123-2", "2000-02-20", "nada.pdf");

INSERT INTO users (id, name, last_name) VALUES (5, "supervisor", "mark");
INSERT INTO supervisors (id) VALUES (5);

INSERT INTO business (id, about_us, email, name) VALUES (1, "somos los mejores", "empresa@empresa.com", "La gran empresa"), (2, "somos los peores", "empresa@empresa.com", "La peor empresa");

INSERT INTO job_offers (id, contract_period, description_offer, position, requirements, responsabilities, salary, vacancy_numbers, valid_date, init_working_day_time, end_working_day_time, id_business) VALUES (1, "2 años", "Excelente ambiente laboral, ven a trabajar como programador", "Junior", "saber java", "actualizar el backend", 300000, 7, "2021-09-27", "09:00", "20:00", 1);
