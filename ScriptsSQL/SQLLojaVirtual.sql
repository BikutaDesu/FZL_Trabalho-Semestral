USE master
GO
DROP DATABASE IF EXISTS lojaVirtual
GO
CREATE DATABASE lojaVirtual
GO
USE lojaVirtual

CREATE TABLE requisitos (
codigo INTEGER NOT NULL IDENTITY(1,1),
so VARCHAR(100) NOT NULL,
armazenamento VARCHAR(10) NOT NULL,
processador VARCHAR(100) NOT NULL,
memoria VARCHAR(10) NOT NULL,
placaVideo VARCHAR(100),
directX VARCHAR(5) NOT NULL
PRIMARY KEY (codigo)
)
GO
CREATE TABLE idiomas (
codigo INTEGER NOT NULL IDENTITY(1,1),
nomeIdioma VARCHAR(25) NOT NULL,
PRIMARY KEY (codigo)
)
GO
CREATE TABLE categorias (
codigo INTEGER NOT NULL IDENTITY(1,1),
nome VARCHAR(25) NOT NULL
PRIMARY KEY (codigo)
)
GO 
CREATE TABLE plataformas (
codigo INTEGER NOT NULL IDENTITY(1,1),
nome VARCHAR(25) NOT NULL,
PRIMARY KEY (codigo)
)
GO
CREATE TABLE usuarios (
CPF CHAR(11) NOT NULL CHECK(LEN(CPF) = 11),
nome VARCHAR(100) NOT NULL, 
email VARCHAR(50) UNIQUE NOT NULL,
senha VARCHAR(30) NOT NULL,
nomeUsuario VARCHAR(20) UNIQUE NOT NULL,
tipoUsuario INTEGER NOT NULL
PRIMARY KEY (CPF)
)
GO
CREATE TABLE funcionarios (
usuarioCPF CHAR(11) NOT NULL,
logradouro VARCHAR(100) NOT NULL,
CEP CHAR(8) NOT NULL,
numPorta CHAR(5) NOT NULL,
salario DECIMAL(7,2)
PRIMARY KEY (usuarioCPF),
FOREIGN KEY (usuarioCPF) REFERENCES usuarios (CPF)
)
GO
CREATE TABLE pedidos (
codigo INTEGER NOT NULL IDENTITY(1,1),
dataPedido DATE NOT NULL,
usuarioCPF CHAR(11) NOT NULL
PRIMARY KEY (codigo),
FOREIGN KEY (usuarioCPF) REFERENCES usuarios (CPF)
)
GO
CREATE TABLE telefones (
telefone CHAR(9) NOT NULL,
usuarioCPF CHAR(11) NOT NULL
FOREIGN KEY (usuarioCPF) REFERENCES usuarios (CPF),
PRIMARY KEY (telefone)
)
GO
CREATE TABLE jogos (
codigo INTEGER NOT NULL IDENTITY(1,1),
nome VARCHAR(100) NOT NULL,
preco DECIMAL(7,2) NOT NULL,
qtdJogo INTEGER NOT NULL,
dataLancamento DATE NOT NULL,
desenvolvedora VARCHAR(100) NOT NULL,
distribuidora VARCHAR(100) NOT NULL,
imagem VARCHAR(100) NOT NULL,
descricao VARCHAR(MAX) NOT NULL,
requisitoCodigo INTEGER NOT NULL,
FOREIGN KEY (requisitoCodigo) REFERENCES requisitos (codigo),
PRIMARY KEY (codigo)
)
GO
CREATE TABLE jogoIdioma (
jogoCodigo INTEGER NOT NULL IDENTITY(1,1),
idiomaCodigo INTEGER NOT NULL
PRIMARY KEY (jogoCodigo, idiomaCodigo),
FOREIGN KEY (jogoCodigo) REFERENCES jogos (codigo),
FOREIGN KEY (idiomaCodigo) REFERENCES idiomas (codigo)
)
GO
CREATE TABLE jogoPlataforma (
jogoCodigo INTEGER NOT NULL,
plataformaCodigo INTEGER NOT NULL
PRIMARY KEY (jogoCodigo, plataformaCodigo)
FOREIGN KEY (jogoCodigo) REFERENCES jogos (codigo),
FOREIGN KEY (plataformaCodigo) REFERENCES plataformas (codigo)
)
GO
CREATE TABLE jogoCategoria (
jogoCodigo INTEGER NOT NULL,
categoriaCodigo INTEGER NOT NULL
PRIMARY KEY (jogoCodigo, categoriaCodigo),
FOREIGN KEY (jogoCodigo) REFERENCES jogos (codigo),
FOREIGN KEY (categoriaCodigo) REFERENCES categorias (codigo)
)
GO
CREATE TABLE jogoPedido (
pedidoCodigo INTEGER NOT NULL,
jogoCodigo INTEGER NOT NULL
PRIMARY KEY (pedidoCodigo, jogoCodigo),
FOREIGN KEY (pedidoCodigo) REFERENCES pedidos (codigo),
FOREIGN KEY (jogoCodigo) REFERENCES jogos (codigo)
)

INSERT INTO requisitos (so, armazenamento, processador, memoria, placaVideo, directX) VALUES 
('Windows 7', '1GB', 'dual core', '500MB', NULL, '11'),
('Windows 7 ou superior', '48GB', 'Intel core i5-4460', '8GB', 'NVIDIA GeForce GTX 760', '11'),
('Windows Vista ou superior', '500MB', '2Ghz', '2GB', NULL, '10'),
('Windows 10', '20GB', 'Intel Core i5 3000', '8GB', 'NVIDIA GeForce GTX 660', '9'),
('Windows 10', '40GB', 'Intel Core i7 4770', '16GB', 'NVIDIA GTX 1060', '12'),
('Windows 7 ou superior', '46GB', 'Ryzen 3 1200', '8GB', 'NVIDIA GeForce GTX 660', '11'),
('Windows 7 ou superior', '25GB', 'Intel Core i3 2100', '4GB', 'NVIDIA GeForce GTX 760', '11'),
('Windows 7 ou superior', '25GB', 'Intel Core i3 2100', '4GB', 'NVIDIA GeForce GTX 750 Ti', '11'),
('Windows 10', '9GB', 'Intel core 2 Duo E5200', '4GB', 'GeForce 9800GTX', '10'),
('Windows 7 ou superior', '55GB', 'Intel Core i7 6700', '16GB', 'NVIDIA GTX 1070', '11'),
('Windows Vista ou Xp', '8GB', 'Dual Core', '8GB', NULL, '9'),
('Windows 7 ou superior', '40GB', 'Intel Core2 Duo', '2GB', NULL, '11'),
('Windows 7 ou superior', '6GB', 'Intel Core i5 3470', '4GB', 'GeForce GTX 650', '11'),
('Windows 7 ou 10', '20GB', 'Intel Core i5-750', '8GB', 'NVIDIA GeForce GTX 670', '11')

INSERT INTO idiomas (nomeIdioma) VALUES
('Português (Brasil)'),
('Inglês'),
('Francês'),	
('Italiano'),
('Alemão'),
('Espanhol (Espanha)'),
('Árabe'),
('Polonês'),
('9Russo'),
('Chinês simplificado'),
('Espanhol (América Latina)'),
('Chinês tradicional')

INSERT INTO categorias (nome) VALUES
('Ação'),
('Anime'),
('Aventura'),
('Fantasia'),
('Luta'),
('RPG'),
('2D')

INSERT INTO plataformas(nome) VALUES
('PC'),
('PS4'),
('PS3'),
('XBOX ONE'),
('XBOX 360')

INSERT INTO usuarios(CPF, nome, email, senha, nomeUsuario, tipoUsuario) VALUES
('12345678912', 'Victor Neves', 'victor@teste.com', '12345', 'neves.v', 1),
('45879121531', 'Raizer Varela', 'raizer@top.com', '123456', 'rai.v', 2),
('84983993993', 'Kesia Top', 'Kesia@top.com', '123456', 'kes.t', 2),
('39239488848', 'Pedro Top', 'Pedro@top.com', '123456', 'per.t', 2),
('29347838888', 'Ana Carolina', 'ana@teste.com', '123456', 'ana.t', 2),
('74393472537', 'Rogerio Auguto', 'rogerio@funcionario.com', '123456', 'rog.t', 1),
('74238647373', 'Gabriel Alves', 'gabriel@funcionario.com', '123456', 'gab.t', 1)

INSERT INTO funcionarios VALUES 
('12345678912', 'Rua José Oiticica Filho', '08210510', '381', 1200.00),
('74393472537', 'Rua Virginea de Miranda', '08240010', '855', 1200.00),
('74238647373', 'Av.Alameda dos Nhabiquaras', '05698470', '1392', 1200.00)

INSERT INTO pedidos (dataPedido, usuarioCPF) VALUES
('2019-09-04', '45879121531'),
('2020-06-01', '84983993993'),
('2020-03-02', '39239488848'),
('2020-02-03', '29347838888')

INSERT INTO telefones (telefone, usuarioCPF)VALUES
('996500997', '12345678912'),
('985134593', '45879121531'),
('973463279', '84983993993'),
('939486362', '39239488848'),
('947352533', '29347838888'),
('985230232', '74393472537'),
('985547221', '74238647373')


-- Consulta de funcionários
SELECT u.CPF, u.nome, u.email, u.senha, u.nomeUsuario, u.tipoUsuario, f.logradouro, f.numPorta, f.CEP, f.salario FROM funcionarios f
INNER JOIN usuarios u
ON u.CPF = f.usuarioCPF
WHERE u.CPF = '12345678912'

-- Consulta de Telefones de um usuário
SELECT * FROM telefones WHERE usuarioCPF = '12345678912'

-- Consulta de Jogos
SELECT * FROM jogos j 
INNER JOIN requisitos r
ON j.requisitoCodigo = r.codigo

-- Consulta de Pedidos
SELECT * FROM pedidos p
INNER JOIN usuarios u
ON p.usuarioCPF = u.CPF
WHERE codigo = '' ;
