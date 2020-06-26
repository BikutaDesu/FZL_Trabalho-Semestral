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

INSERT INTO usuarios(CPF, nome, email, senha, nomeUsuario, tipoUsuario) 
VALUES ('12345678912', 'Victor Neves', 'victor@teste.com', '12345', 'neves.v', 1)

INSERT INTO funcionarios VALUES ('12345678912', 'Rua José Oiticica Filho', '08210510', '381', 1200.00)

INSERT INTO telefones VALUES('996500997', '12345678912')
INSERT INTO telefones VALUES('985134593', '12345678912')

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
