INSERT INTO roles (id, `role`) VALUES (1, "ROLE_SUPERVISOR"), (2, "ROLE_POSTULANT");
INSERT INTO credentials (id, username, password, enabled) VALUES (1, "supervisor", "$2a$10$sU5v1QsfyRaB91Czc1hRMOohZrE5zVFJEi90i4y/wrRgWgXxzX52y", true), (2, "postulante", "$2a$10$tlKwRbWmWt49lgVkvza5tu.8NX9WHVfa3RrjNZ6SliLyOlRbjEMNG", true);
INSERT INTO credencial_roles (id_credencial, id_role) VALUES (1, 1), (2, 2);

INSERT INTO users (id, name, last_name, id_credencial) VALUES (1, "Milo", "Chil", 2);
INSERT INTO applicants (id, web, phone_number, run, email, date_of_birth, curriculum_vitae) VALUES (1, "www.paginaweb.com", "+56934231234", "19.234.123-2", "milo@chil.com", "2000-02-20", "nada.pdf");
INSERT INTO users (id, name, last_name) VALUES (2, "Marta", "Rosa");
INSERT INTO applicants (id, web, phone_number, run, email, date_of_birth, curriculum_vitae) VALUES (2, "www.paginaweb.com", "+56934231234", "19.234.123-2", "marta@rosa.com", "2000-02-20", "nada.pdf");
INSERT INTO users (id, name, last_name) VALUES (3, "Natalia", "Estrella");
INSERT INTO applicants (id, web, phone_number, run, email, date_of_birth, curriculum_vitae) VALUES (3, "www.paginaweb.com", "+56934231234", "19.234.123-2", "natalia@estrella.com", "2000-02-20", "nada.pdf");
INSERT INTO users (id, name, last_name) VALUES (4, "Roxy", "Rus");
INSERT INTO applicants (id, web, phone_number, run, email, date_of_birth, curriculum_vitae) VALUES (4, "www.paginaweb.com", "+56934231234", "19.234.123-2", "roxy@rus.com", "2000-02-20", "nada.pdf");

INSERT INTO users (id, name, last_name) VALUES (5, "supervisor", "mark");
INSERT INTO supervisors (id) VALUES (5);

INSERT INTO business (id, about_us, email, name, enable) VALUES (1, "somos los mejores", "empresa@empresa.com", "La gran empresa", true), (2, "somos los peores", "empresa@empresa.com", "La peor empresa", true);

INSERT INTO job_offers (id, contract_period, description_offer, position, requirements, responsabilities, salary, vacancy_numbers, valid_date, init_working_day_time, end_working_day_time, id_business, enabled) VALUES (1, "2 años", "Excelente ambiente laboral, ven a trabajar como programador", "Junior", "saber java", "actualizar el backend", 300000, 7, "2021-09-27", "09:00", "20:00", 1, true), (2, "3 años", "Excelente aprendizaje, ven a trabajar como devops", "Junior", "saber GCP", "subir a producción", 300000, 7, "2021-09-27", "09:00", "20:00", 1, true);

INSERT INTO postulate (id_job_offer, id_postulant) VALUES (1, 1);