INSERT INTO developers(first_name, last_name, age, salary) VALUES
('Artem', 'Morozov', 17, 350),
('John', 'Snow', 31, 2000),
('Van', 'Darkholm', 49, 4500),
('Elena', 'Optimus', 25, 1489);

INSERT INTO skills (branch, skill) VALUES
('Java', 'Junior'),
('Java', 'Middle'),
('Java', 'Senior'),
('C++', 'Junior'),
('C++', 'Middle'),
('C++', 'Senior'),
('C#', 'Junior'),
('C#', 'Middle'),
('C#', 'Senior'),
('JS', 'Junior'),
('JS', 'Middle'),
('JS', 'Senior');


INSERT INTO skills_developers (developer_id, skill_id) VALUES
(1,1),
(2,3),
(3,3),
(3,8),
(4,1);

INSERT INTO projects(name, cost, foundation_date) VALUES
('Fortnite 2', 200000, '2017-07-21'),
('Minecraft 1.20', 180000, '2011-11-18'),
('Windwos 11', 300, '2021-10-05'),
('ScumBox', 10000, '2016-01-01');

INSERT INTO developers_projects(developer_id, project_id) VALUES
(1,1),
(1,2),
(2,2),
(2,4),
(3,1),
(3,3),
(4,3),
(4,4);

INSERT INTO companies(name, CEO) VALUES
('Epig Games', 'Tim Suini'),
('Mojang', 'jeb_');

INSERT INTO projects_companies (company_id, project_id) VALUES
(1,1),
(1,3),
(2,2),
(2,4);

INSERT INTO customers(first_name, last_name, phone_number) VALUES
('Mile','Vazovski', '+390998887654'),
('Krip','Kripovich', '048133088');

INSERT INTO project_customers VALUES
(1,3),
(1,4),
(2,1),
(2,2);