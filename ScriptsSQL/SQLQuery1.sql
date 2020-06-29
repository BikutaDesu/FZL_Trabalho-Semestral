SELECT	SUBSTRING(u.CPF,1,3)+'.'+SUBSTRING(u.CPF,4,3)+'.'+SUBSTRING(u.CPF,7,3)+'-'+SUBSTRING(u.CPF,10,2) AS CPF, 
		u.nome, u.email, u.nomeUsuario, f.logradouro, f.numPorta, 
		SUBSTRING(f.CEP,1,5)+'-'+SUBSTRING(f.CEP,6,8) AS CEP, f.salario FROM funcionarios f 
INNER JOIN usuarios u
ON u.CPF = f.usuarioCPF
WHERE u.nome like ?

SELECT t.telefone
FROM telefones t
WHERE usuarioCPF = ?

SELECT telefone
FROM telefones
WHERE telefone = ?

SELECT * FROM usuarios
SELECT * FROM funcionarios
