/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Andressa
 * Created: 07/05/2018
 */

use Guia;

create table usuario (
	idUsuario integer not null auto_increment,
	nome varchar(140) not null,
	dataNascimento date not null,
	idioma varchar(140) not null,
	ocupacao varchar(140) not null,
	email varchar(300) not null,
	senha varchar (300) not null,
	localizacao varchar(300),
	statusConta varchar(50),
	primary key(idUsuario)
);

describe usuario;

insert into usuario (nome, dataNascimento, idioma, ocupacao, email, senha, localizacao, statusConta) 
values("Usuário", 04/04/2000, "Português BR", "Estagiário", "usuario@teste.com", "123123", "São Paulo, SP - Brasil", "ativa");
