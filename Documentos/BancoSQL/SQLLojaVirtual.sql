CREATE DATABASE lojaVirtual
GO
USE lojaVirtual

CREATE TABLE Requisitos (
IDRequisitos INTEGER not null,
SO VARCHAR(100) not null,
Armazenamento VARCHAR(10) not null,
Processador VARCHAR(100) not null,
Memoria VARCHAR(10) not null,
PlacaVideo VARCHAR(100),
DirectX VARCHAR(5) not null
PRIMARY KEY (IDRequisitos)
)
GO
CREATE TABLE Idioma (
IDIdioma INTEGER not null,
NomeIdioma VARCHAR(25) not null,
PRIMARY KEY (IDIdioma)
)
GO
CREATE TABLE TipoUsuario (
IDTipo INTEGER not null,
Nome VARCHAR(30) not null,
PRIMARY KEY (IDTipo)
)
GO
CREATE TABLE Categoria (
IDCategoria INTEGER not null,
Nome VARCHAR(25) not null
PRIMARY KEY (IdCategoria)
)
GO 
CREATE TABLE Plataforma (
IDPlataforma INTEGER not null,
Nome VARCHAR(25) not null,
PRIMARY KEY (IDPlataforma)
)
GO
CREATE TABLE Usuario (
CPF INTEGER not null,
Nome VARCHAR(100) not null, 
Email VARCHAR(50) not null,
Senha VARCHAR(30) not null,
NomeUsuario VARCHAR(20) not null,
TipoUsuarioIDTipo INTEGER not null
FOREIGN KEY (TipoUsuarioIDTipo) REFERENCES TipoUsuario (IDTipo),
PRIMARY KEY (CPF)
)
GO
CREATE TABLE Funcionarios (
UsuarioCPF INTEGER not null,
Logradouro VARCHAR(100) not null,
CEP CHAR(8) not null,
NumPorta CHAR(5) not null,
Salario DECIMAL(7,2)
PRIMARY KEY (UsuarioCPF),
FOREIGN KEY (UsuarioCPf) REFERENCES Usuario (CPF)
)
GO
CREATE TABLE Pedido (
IDPedido INTEGER not null,
DtPedido DATE not null,
ClienteCPF INTEGER not null
PRIMARY KEY (IDPedido)
)
GO
CREATE TABLE Telefone (
Telefone INTEGER not null,
ClienteCPF INTEGER not null
FOREIGN KEY (ClienteCPF) REFERENCES Usuario (CPF),
PRIMARY KEY (Telefone)
)
GO
CREATE TABLE Jogo (
IDJogo INTEGER not null,
Nome VARCHAR(100) not null,
Preco DECIMAL(7,2) not null,
QtdJogo INTEGER not null,
DtLancamento DATE not null,
Desenvolvedora VARCHAR(100) not null,
Distribuidora VARCHAR(100) not null,
Imagem VARCHAR(100) not null,
Descricao VARCHAR(MAX) not null,
RequisitosIDRequisitos INTEGER not null,
FuncionarioUsuarioCPF INTEGER not null
FOREIGN KEY (RequisitosIDRequisitos) REFERENCES Requisitos (IDRequisitos),
FOREIGN KEY (FuncionarioUsuarioCPF) REFERENCES Usuario (CPF),
PRIMARY KEY (IDJogo)
)
GO
CREATE TABLE Jogo_Idioma (
JogoIDJogo INTEGER not null,
IdiomaIDIdioma INTEGER not null
PRIMARY KEY (JogoIDJogo, IdiomaIDIdioma),
FOREIGN KEY (JogoIDJogo) REFERENCES Jogo (IDJogo),
FOREIGN KEY (IdiomaIDIdioma) REFERENCES Idioma (IDIdioma)
)
GO
CREATE TABLE Jogo_Plataforma (
JogoIDJogo INTEGER not null,
PlataformaIDPlataforma INTEGER not null
PRIMARY KEY (JogoIDJogo, PlataformaIDPlataforma)
FOREIGN KEY (JogoIDJogo) REFERENCES Jogo (IDJogo),
FOREIGN KEY (PlataformaIDPlataforma) REFERENCES Plataforma (IDPlataforma)
)
GO
CREATE TABLE Jogo_Categoria (
JogoIDJogo INTEGER not null,
CategoriaIDCategoria INTEGER not null
PRIMARY KEY (JogoIDJogo, CategoriaIDCategoria),
FOREIGN KEY (JogoIDJogo) REFERENCES Jogo (IDJogo),
FOREIGN KEY (CategoriaIDCategoria) REFERENCES Categoria (IDCategoria)
)
GO
CREATE TABLE Jogo_Pedido (
PedidoIDPedido INTEGER not null,
JogoIDJogo INTEGER not null
PRIMARY KEY (PedidoIDPedido, JogoIdJogo),
FOREIGN KEY (PedidoIDPedido) REFERENCES Pedido (IDPedido),
FOREIGN KEY (JogoIdJogo) REFERENCES Jogo (IDJogo)
)

