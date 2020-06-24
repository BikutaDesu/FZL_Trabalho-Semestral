CREATE DATABASE lojaVirtual
GO
USE lojaVirtual

CREATE TABLE requisitos (
codigo INTEGER not null IDENTITY(1,1),
so VARCHAR(100) not null,
armazenamento VARCHAR(10) not null,
processador VARCHAR(100) not null,
memoria VARCHAR(10) not null,
placaVideo VARCHAR(100),
directX VARCHAR(5) not null
PRIMARY KEY (codigo)
)
GO
CREATE TABLE idiomas (
codigo INTEGER not null IDENTITY(1,1),
nomeIdioma VARCHAR(25) not null,
PRIMARY KEY (codigo)
)
GO
CREATE TABLE categorias (
codigo INTEGER not null IDENTITY(1,1),
nome VARCHAR(25) not null
PRIMARY KEY (codigo)
)
GO 
CREATE TABLE plataformas (
codigo INTEGER not null IDENTITY(1,1),
nome VARCHAR(25) not null,
PRIMARY KEY (codigo)
)
GO
CREATE TABLE usuarios (
CPF INTEGER not null,
nome VARCHAR(100) not null, 
email VARCHAR(50) not null,
senha VARCHAR(30) not null,
nomeUsuario VARCHAR(20) not null,
tipoUsuario INTEGER not null
PRIMARY KEY (CPF)
)
GO
CREATE TABLE funcionarios (
usuarioCPF INTEGER not null,
logradouro VARCHAR(100) not null,
CEP CHAR(8) not null,
numPorta CHAR(5) not null,
salario DECIMAL(7,2)
PRIMARY KEY (usuarioCPF),
FOREIGN KEY (usuarioCPF) REFERENCES usuarios (CPF)
)
GO
CREATE TABLE pedidos (
codigo INTEGER not null IDENTITY(1,1),
dataPedido DATE not null,
usuarioCPF INTEGER not null
PRIMARY KEY (codigo),
FOREIGN KEY (usuarioCPF) REFERENCES usuarios (CPF)
)
GO
CREATE TABLE telefones (
telefone INTEGER not null,
usuarioCPF INTEGER not null
FOREIGN KEY (usuarioCPF) REFERENCES usuarios (CPF),
PRIMARY KEY (telefone)
)
GO
CREATE TABLE jogos (
codigo INTEGER not null IDENTITY(1,1),
nome VARCHAR(100) not null,
preco DECIMAL(7,2) not null,
qtdJogo INTEGER not null,
dataLancamento DATE not null,
desenvolvedora VARCHAR(100) not null,
distribuidora VARCHAR(100) not null,
imagem VARCHAR(100) not null,
descricao VARCHAR(MAX) not null,
requisitoCodigo INTEGER not null,
funcionarioCPF INTEGER not null
FOREIGN KEY (requisitoCodigo) REFERENCES requisitos (codigo),
FOREIGN KEY (funcionarioCPF) REFERENCES usuarios (CPF),
PRIMARY KEY (codigo)
)
GO
CREATE TABLE jogoIdioma (
jogoCodigo INTEGER not null IDENTITY(1,1),
idiomaCodigo INTEGER not null
PRIMARY KEY (jogoCodigo, idiomaCodigo),
FOREIGN KEY (jogoCodigo) REFERENCES jogos (codigo),
FOREIGN KEY (idiomaCodigo) REFERENCES idiomas (codigo)
)
GO
CREATE TABLE jogoPlataforma (
jogoCodigo INTEGER not null,
plataformaCodigo INTEGER not null
PRIMARY KEY (jogoCodigo, plataformaCodigo)
FOREIGN KEY (jogoCodigo) REFERENCES jogos (codigo),
FOREIGN KEY (plataformaCodigo) REFERENCES plataformas (codigo)
)
GO
CREATE TABLE jogoCategoria (
jogoCodigo INTEGER not null,
categoriaCodigo INTEGER not null
PRIMARY KEY (jogoCodigo, categoriaCodigo),
FOREIGN KEY (jogoCodigo) REFERENCES jogos (codigo),
FOREIGN KEY (categoriaCodigo) REFERENCES categorias (codigo)
)
GO
CREATE TABLE jogoPedido (
pedidoCodigo INTEGER not null,
jogoCodigo INTEGER not null
PRIMARY KEY (pedidoCodigo, jogoCodigo),
FOREIGN KEY (pedidoCodigo) REFERENCES pedidos (codigo),
FOREIGN KEY (jogoCodigo) REFERENCES jogos (codigo)
)

