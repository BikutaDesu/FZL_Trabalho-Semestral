USE master
GO
DROP DATABASE IF EXISTS lojaVirtual
GO
CREATE DATABASE lojaVirtual
GO
USE lojaVirtual

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
nome VARCHAR(25) NOT NULL
PRIMARY KEY (codigo)
)
GO
CREATE TABLE usuarios (
CPF CHAR(11) NOT NULL CHECK(LEN(CPF) = 11),
nome VARCHAR(100) NOT NULL, 
email VARCHAR(50) UNIQUE NOT NULL,
senha VARCHAR(30) NOT NULL,
nomeUsuario VARCHAR(20) UNIQUE NOT NULL
PRIMARY KEY (CPF)
)
GO
CREATE TABLE funcionarios (
usuarioCPF CHAR(11) NOT NULL,
logradouro VARCHAR(100) NOT NULL,
CEP CHAR(8) NOT NULL,
numPorta CHAR(5) NOT NULL,
salario DECIMAL(7,2)
PRIMARY KEY (usuarioCPF)
FOREIGN KEY (usuarioCPF) REFERENCES usuarios (CPF)
)
GO
CREATE TABLE pedidos (
codigo INTEGER NOT NULL IDENTITY(1,1),
dataPedido DATE NOT NULL,
usuarioCPF CHAR(11) NOT NULL
PRIMARY KEY (codigo)
FOREIGN KEY (usuarioCPF) REFERENCES usuarios (CPF)
)
GO
CREATE TABLE telefones (
telefone CHAR(9) NOT NULL,
usuarioCPF CHAR(11) NOT NULL
PRIMARY KEY (telefone)
FOREIGN KEY (usuarioCPF) REFERENCES usuarios (CPF)
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
imagem VARCHAR(MAX) NOT NULL,
descricao VARCHAR(MAX) NOT NULL
PRIMARY KEY (codigo)
)
GO
CREATE TABLE requisitos (
codigo INTEGER NOT NULL,
so VARCHAR(100) NOT NULL,
armazenamento VARCHAR(10) NOT NULL,
processador VARCHAR(100) NOT NULL,
memoria VARCHAR(10) NOT NULL,
placaVideo VARCHAR(100),
directX VARCHAR(5) NOT NULL
PRIMARY KEY (codigo)
FOREIGN KEY (codigo) REFERENCES jogos (codigo)
)
GO
CREATE TABLE jogoIdioma (
jogoCodigo INTEGER NOT NULL,
idiomaCodigo INTEGER NOT NULL
PRIMARY KEY (jogoCodigo, idiomaCodigo)
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
PRIMARY KEY (jogoCodigo, categoriaCodigo)
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
('12345678912', 'Victor Neves', 'victor@teste.com', '12345', 'neves.v'),
('45879121531', 'Raizer Varela', 'raizer@top.com', '123456', 'rai.v'),
('84983993993', 'Kesia Top', 'Kesia@top.com', '123456', 'kes.t'),
('39239488848', 'Pedro Top', 'Pedro@top.com', '123456', 'per.t'),
('29347838888', 'Ana Carolina', 'ana@teste.com', '123456', 'ana.t'),
('74393472537', 'Rogerio Auguto', 'rogerio@funcionario.com', '123456', 'rog.t'),
('74238647373', 'Gabriel Alves', 'gabriel@funcionario.com', '123456', 'gab.t')

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

INSERT INTO jogos (nome, preco, qtdJogo, dataLancamento, desenvolvedora, distribuidora, imagem, descricao) VALUES
('Missile Command', 5.99, 25, '2020-06-24', 'Nickervision Studios' , 'Distribuidora X', 'Missile_Command.jpg', 'Missile Command: Recharged é uma nova versão do amado e agitado clássico de fliperama'),
('Monster Hunter World', 99.99, 30, '2019-03-11', 'CAPCOM', 'CAPCOM', 'Monster_Hunter_World.jpg', 'Conheça o Novo Mundo! Em Monster Hunter: World, o jogo mais recente da série.'),
('Stardew Valley', 14.99, 50, '2016-02-26', 'ConcernedApe', 'ConcernedApe', 'Stardew_Valley.jpg', 'Você herdou a antiga fazenda do seu avô, em Stardew Valley.'),
('Saint Row The Third Remastered', 4.99, 75, '2016-02-24','Volition', 'Deep Silver', 'Saint_Row_The_Third_Remastered.jpg', 'Prepare-se para as situações mais loucas já vistas quando os Third Street Saints decidem enfrentar o Syndicate!'),
('Tomb Raider Definitive Edition', 58.99, 47, '2017-02-24', 'Eidos-Montréal', 'Square Enix', 'Tomb_Raider_Definitive_Edition.jpg', 'Em Shadow of the Tomb Raider Definitive Edition, acompanhe o capítulo final da origem de Lara e testemunhe'),
('Assassins Creed Odysssey', 89.99, 51, '2019-12-24', 'Rafaela', 'Distribuidora  Y', 'Assassins_Creed_Odysssey.jpg', 'Prepare-se para as situações mais loucas já vistas quando os Third Street Saints decidem enfrentar o Syndicate!'),
('Sekiro Shadows Die Twice', 47.99, 64, '2020-02-24','Rachel', 'Distribuidora Z', 'Sekiro_Shadows_Die_Twice.jpg', 'Prepare-se para as situações mais loucas já vistas quando os Third Street Saints decidem enfrentar o Syndicate!'),
('Dark Souls III', 42.99, 93, '2016-05-12', 'Mario', 'Distribuidora S', 'Dark_Souls_III.jpg', 'Prepare-se para as situações mais loucas já vistas quando os Third Street Saints decidem enfrentar o Syndicate!'),
('Hollow Knight', 78.99, 37, '2019-09-24','Sofia', 'Distribuidora F', 'Hollow_Knight.jpg', 'Prepare-se para as situações mais loucas já vistas quando os Third Street Saints decidem enfrentar o Syndicate!'),
('Star Wars Jedi Fallen Order', 65.99, 30, '2014-02-24','Noah', 'Distribuidora R', 'Star_Wars_Jedi_Fallen_Order.jpg', 'Prepare-se para as situações mais loucas já vistas quando os Third Street Saints decidem enfrentar o Syndicate!'),
('Batman Arkhan Asylum', 43.99, 32, '2016-12-26', 'Joaquim', 'Distribuidora J', 'Batman_Arkhan_Asylum.jpg','Prepare-se para as situações mais loucas já vistas quando os Third Street Saints decidem enfrentar o Syndicate!'),
('Naruto Shippuden Ultimate Ninja Storm 4', 76.99, 64, '2019-02-12', 'Nathan', 'Distribuidora N', 'Naruto_Shippuden_Ultimate_Ninja_Storm_4.jpg', 'Prepare-se para as situações mais loucas já vistas quando os Third Street Saints decidem enfrentar o Syndicate!'),
('Dragon Ball FighterZ', 83.99, 89, '2020-05-24', 'Jorge', 'Distribuidora A', 'Dragon_Ball_FighterZ.jpg', 'Prepare-se para as situações mais loucas já vistas quando os Third Street Saints decidem enfrentar o Syndicate!'),
('Mortal Kombat 11', 49.99, 28, '2018-05-17', 'Diana', 'Distribuidora G', 'Mortal_Kombat_11.jpg', 'Prepare-se para as situações mais loucas já vistas quando os Third Street Saints decidem enfrentar o Syndicate!')

INSERT INTO requisitos VALUES 
(1,'Windows 7', '1GB', 'dual core', '500MB', NULL, '11'),
(2,'Windows 7 ou superior', '48GB', 'Intel core i5-4460', '8GB', 'NVIDIA GeForce GTX 760', '11'),
(3,'Windows Vista ou superior', '500MB', '2Ghz', '2GB', NULL, '10'),
(4,'Windows 10', '20GB', 'Intel Core i5 3000', '8GB', 'NVIDIA GeForce GTX 660', '9'),
(5,'Windows 10', '40GB', 'Intel Core i7 4770', '16GB', 'NVIDIA GTX 1060', '12'),
(6,'Windows 7 ou superior', '46GB', 'Ryzen 3 1200', '8GB', 'NVIDIA GeForce GTX 660', '11'),
(7,'Windows 7 ou superior', '25GB', 'Intel Core i3 2100', '4GB', 'NVIDIA GeForce GTX 760', '11'),
(8,'Windows 7 ou superior', '25GB', 'Intel Core i3 2100', '4GB', 'NVIDIA GeForce GTX 750 Ti', '11'),
(9,'Windows 10', '9GB', 'Intel core 2 Duo E5200', '4GB', 'GeForce 9800GTX', '10'),
(10,'Windows 7 ou superior', '55GB', 'Intel Core i7 6700', '16GB', 'NVIDIA GTX 1070', '11'),
(11,'Windows Vista ou Xp', '8GB', 'Dual Core', '8GB', NULL, '9'),
(12,'Windows 7 ou superior', '40GB', 'Intel Core2 Duo', '2GB', NULL, '11'),
(13,'Windows 7 ou superior', '6GB', 'Intel Core i5 3470', '4GB', 'GeForce GTX 650', '11'),
(14,'Windows 7 ou 10', '20GB', 'Intel Core i5-750', '8GB', 'NVIDIA GeForce GTX 670', '11')

INSERT INTO jogoIdioma (jogoCodigo, idiomaCodigo) VALUES
(1, 5),
(2, 2),
(3, 5),
(4, 1),
(4, 2),
(5, 3),
(5, 2),
(6, 4),
(7, 1),
(8, 2),
(9, 4),
(10, 4),
(11, 1),
(12, 2),
(13, 3),
(14, 5)

INSERT INTO jogoPlataforma (jogoCodigo, plataformaCodigo) VALUES
(1, 5),
(2, 2),
(3, 5),
(4, 1),
(5, 2),
(6, 4),
(7, 1),
(8, 2),
(9, 4),
(10, 4),
(11, 1),
(12, 2),
(13, 3),
(14, 5)

INSERT INTO jogoCategoria (jogoCodigo, categoriaCodigo) VALUES
(1, 1),
(2, 7),
(3, 5),
(4, 4),
(5, 3),
(6, 6),
(7, 4),
(8, 5),
(9, 2),
(10, 1),
(11, 3),
(12, 3),
(13, 5),
(14, 7)

INSERT INTO jogoPedido (jogoCodigo, pedidoCodigo) VALUES
(1, 1),
(6, 2),
(5, 4),
(9, 2),
(2, 3)

/*
UPDATE jogos SET nome=?, preco=?, qtdJogo=?, dataLancamento=?, desenvolvedora=?, distribuidora=?, imagem=?, descricao=?
WHERE jogos.codigo like ?

SELECT	codigo, nome, preco, qtdJogo, CONVERT(varchar, dataLancamento ,103) AS dataLancamento, desenvolvedora, distribuidora,
		imagem, descricao
FROM jogos 
WHERE nome like ?
*/


--SELECT FUNCIONARIO
SELECT 	SUBSTRING(u.CPF,1,3)+'.'+SUBSTRING(u.CPF,4,3)+'.'+SUBSTRING(u.CPF,7,3)+'-'+SUBSTRING(u.CPF,10,2) AS CPF, 
		u.nome, u.email, u.nomeUsuario, f.logradouro, f.numPorta, 
		SUBSTRING(f.CEP,1,5)+'-'+SUBSTRING(f.CEP,6,8) AS CEP, 
		f.salario 
FROM funcionarios f 
INNER JOIN usuarios u 
ON u.CPF = f.usuarioCPF 
WHERE u.nome like ?

--SELECT USUARIO(CLIENTE)
SELECT 	SUBSTRING(u.CPF,1,3)+'.'+SUBSTRING(u.CPF,4,3)+'.'+SUBSTRING(u.CPF,7,3)+'-'+SUBSTRING(u.CPF,10,2) AS CPF, 
		u.nome, u.email, u.nomeUsuario 
FROM usuarios u LEFT OUTER JOIN funcionarios f 
ON u.CPF = f.usuarioCPF 
WHERE f.usuarioCPF IS NULL AND nome like ?

--CONSULTA DE TELEFONE DE UM USUARIO
SELECT SUBSTRING(telefone ,1,5)+'-'+SUBSTRING(telefone ,6,9) AS telefone 
FROM telefones 
WHERE usuarioCPF = ?

--BUSCA OS NOMES DOS IDIOMAS RELACIONADOS AO JOGO
SELECT i.codigo, i.nomeIdioma 
FROM idiomas i INNER JOIN jogoIdioma ij
ON i.codigo = ij.idiomaCodigo
INNER JOIN jogos j
ON j.codigo = ij.jogoCodigo
WHERE j.codigo = ?

--BUSCAR JOGO PELO NOME
SELECT	codigo, nome, preco, qtdJogo, CONVERT(varchar, dataLancamento ,103) AS dataLancamento, 
		desenvolvedora, distribuidora, imagem, descricao
FROM jogos 
WHERE nome like ?

--BUSCAR JOGO E REQUISITOS
SELECT * FROM jogos j 
INNER JOIN requisitos r
ON j.codigo = r.codigo

--BUSCA O NOME DAS CATEGORIAS RELACIONADAS AO JOGO
SELECT c.codigo, c.nome
FROM categorias c INNER JOIN jogoCategoria jc
ON c.codigo = jc.categoriaCodigo
INNER JOIN jogos j
ON j.codigo = jc.jogoCodigo
WHERE j.codigo = ? 

--BUSCA O NOME DAS PLATAFORMAS RELACIONADAS AO JOGO
SELECT p.codigo, p.nome 
FROM plataformas p INNER JOIN jogoPlataforma jp 
ON p.codigo = jp.plataformaCodigo 
INNER JOIN jogos j 
ON j.codigo = jp.jogoCodigo 
WHERE j.codigo = ?

--SELECT FUNCIONARIOS
SELECT 	SUBSTRING(u.CPF,1,3)+'.'+SUBSTRING(u.CPF,4,3)+'.'+SUBSTRING(u.CPF,7,3)+'-'+SUBSTRING(u.CPF,10,2) AS CPF, 
		u.nome, u.email, u.nomeUsuario, f.logradouro, f.numPorta, 
		SUBSTRING(f.CEP,1,5)+'-'+SUBSTRING(f.CEP,6,8) AS CEP, f.salario FROM funcionarios f 
INNER JOIN usuarios u
ON u.CPF = f.usuarioCPF
WHERE u.nome like ?

--SELECT USUARIO
SELECT	SUBSTRING(CPF,1,3)+'.'+SUBSTRING(CPF,4,3)+'.'+SUBSTRING(CPF,7,3)+'-'+SUBSTRING(CPF,10,2) AS CPF, 
		nome, email, nomeUsuario 
FROM usuarios
WHERE CPF = ?

--SELECT PEDIDOS DE UM CLIENTE
SELECT 	jp.pedidoCodigo, j.nome, p.dataPedido, j.preco 
FROM usuarios u INNER JOIN pedidos p
ON u.CPF = p.usuarioCPF
INNER JOIN jogoPedido jp
ON jp.pedidoCodigo = p.codigo
INNER JOIN jogos j
ON j.codigo = jp.jogoCodigo
WHERE u.CPF = ?

--SELECT TELEFONE DE UM USUARIO
SELECT 	SUBSTRING(telefone ,1,5)+'-'+SUBSTRING(telefone ,6,9) AS telefone
FROM telefones
WHERE usuarioCPF = ?

--PESQUISA DE JOGOS POR NOME, IDIOMA, CATEGORIA, PLATAFORMA
SELECT DISTINCT j.codigo, j.nome, j.preco, j.qtdJogo, j.imagem 
FROM jogos j 
INNER JOIN jogoIdioma ji
ON ji.jogoCodigo = j.codigo
INNER JOIN jogoCategoria jc
ON jc.jogoCodigo = j.codigo
INNER JOIN jogoPlataforma jp
ON jp.jogoCodigo = j.codigo
WHERE j.nome like ?
AND ji.idiomaCodigo = ?
AND jc.categoriaCodigo = ?
AND jp.plataformaCodigo = ?

--SELECIONANDO USUARIOS QUE NÃO SÃO FUNCIONARIOS (CLIENTES)
SELECT	SUBSTRING(u.CPF,1,3)+'.'+SUBSTRING(u.CPF,4,3)+'.'+SUBSTRING(u.CPF,7,3)+'-'+SUBSTRING(u.CPF,10,2) AS CPF,
		u.nome, u.email, u.nomeUsuario
FROM usuarios u LEFT OUTER JOIN funcionarios f
ON u.CPF = f.usuarioCPF
WHERE f.usuarioCPF IS NULL AND nome like ?

--SELECIONANDO CLIENTES QUE NÃO TEM PEDIDOS CADASTRADOS
SELECT	SUBSTRING(u.CPF,1,3)+'.'+SUBSTRING(u.CPF,4,3)+'.'+SUBSTRING(u.CPF,7,3)+'-'+SUBSTRING(u.CPF,10,2) AS CPF,
		u.nome, u.email, u.nomeUsuario
FROM usuarios u LEFT OUTER JOIN funcionarios f
ON u.CPF = f.usuarioCPF
LEFT OUTER JOIN pedidos p
ON u.CPF = p.usuarioCPF
WHERE f.usuarioCPF IS NULL 
AND p.usuarioCPF IS NULL
AND nome like ?

--LOGIN FUNCIONARIOS
SELECT u.CPF, u.nome, u.email, u.nomeUsuario 
FROM funcionarios f 
INNER JOIN usuarios u 
ON u.CPF = f.usuarioCPF 
WHERE email=? AND senha=?

--LOGIN CLIENTES
SELECT u.CPF, u.nome, u.email, u.nomeUsuario 
FROM usuarios u  
LEFT OUTER JOIN funcionarios f 
ON u.CPF = f.usuarioCPF 
WHERE email=? AND senha=?
AND f.usuarioCPF IS NULL

--SOMA TOTAL DE CADA PEDIDO DE UM USUARIO(CLIENTE)
SELECT	CONVERT(varchar, p.dataPedido ,103) AS Data_Pedido, 
		u.nome, 
		SUM(j.preco) AS Valor_Total
FROM usuarios u INNER JOIN pedidos p
ON u.CPF = p.usuarioCPF
INNER JOIN jogos j
ON j.codigo = p.codigo
GROUP BY p.dataPedido, u.nome, u.CPF
HAVING u.CPF = ?