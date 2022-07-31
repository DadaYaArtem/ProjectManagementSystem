CREATE TABLE IF NOT EXISTS skills(
     id IDENTITY PRIMARY KEY,
     branch VARCHAR(50) CHECK (branch IN ('Java', 'C++', 'C#', 'JS')),
     skill VARCHAR(50) CHECK (skill IN ('Junior', 'Middle', 'Senior'))
);

 CREATE TABLE IF NOT EXISTS developers(
    id IDENTITY PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    age TINYINT,
    salary INT
);

CREATE TABLE IF NOT EXISTS skills_developers(
    skill_id BIGINT,
    developer_id BIGINT,
    PRiMARY KEY(skill_id, developer_id),
    FOREIGN KEY (skill_id) REFERENCES skills(id),
    FOREIGN KEY (developer_id) REFERENCES developers(id)
);

CREATE TABLE IF NOT EXISTS projects(
    id IDENTITY PRIMARY KEY,
    name VARCHAR(20) ,
    cost INT,
    foundation_date DATE
);

CREATE TABLE IF NOT EXISTS developers_projects(
    developer_id BIGINT,
    project_id BIGINT,
    PRiMARY KEY(developer_id, project_id),
    FOREIGN KEY (developer_id) REFERENCES developers(id),
    FOREIGN KEY (project_id) REFERENCES projects(id)
);

CREATE TABLE IF NOT EXISTS customers(
    id IDENTITY PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    phone_number VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS project_customers(
    customer_id BIGINT ,
    project_id BIGINT ,
    PRiMARY KEY(project_id, customer_id),
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (project_id) REFERENCES projects(id)
);

CREATE TABLE IF NOT EXISTS companies(
    id IDENTITY PRIMARY KEY,
    name VARCHAR(20) ,
    CEO VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS projects_companies(
    company_id BIGINT ,
    project_id BIGINT ,
    PRiMARY KEY(company_id, project_id),
    FOREIGN KEY (company_id) REFERENCES companies(id),
    FOREIGN KEY (project_id) REFERENCES projects(id)
);

