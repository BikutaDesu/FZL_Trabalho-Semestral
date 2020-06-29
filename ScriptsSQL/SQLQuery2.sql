select * from jogoPedido

select * from pedidos inner join jogoPedido
on pedidos.codigo = jogoPedido.pedidoCodigo

SELECT codigo, nome, preco, qtdJogo, imagem 
FROM jogos

select * from telefones
SELECT SUBSTRING(telefone ,1,5)+'-'+SUBSTRING(telefone ,6,9) AS telefone
FROM telefones
WHERE usuarioCPF = ?

SELECT jp.pedidoCodigo, j.nome, p.dataPedido, j.preco 
FROM usuarios u INNER JOIN pedidos p
ON u.CPF = p.usuarioCPF
INNER JOIN jogoPedido jp
ON jp.pedidoCodigo = p.codigo
INNER JOIN jogos j
ON j.codigo = jp.jogoCodigo
WHERE u.CPF = ?


SELECT	SUBSTRING(CPF,1,3)+'.'+SUBSTRING(CPF,4,3)+'.'+SUBSTRING(CPF,7,3)+'-'+SUBSTRING(CPF,10,2) AS CPF, 
		nome, email, nomeUsuario 
FROM usuarios
WHERE CPF = ?
