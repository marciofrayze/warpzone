;=======================================================================================
;Copyright (C) 2005  Márcio Frayze David e Marcelo Baccelli
;
;This program is free software; you can redistribute it and/or
;modify it under the terms of the GNU General Public License
;as published by the Free Software Foundation; either version 2
;of the License, Or any later version.
;
;This program is distributed in the hope that it will be useful,
;but WITHOUT ANY WARRANTY; without even the implied warranty of
;MERCHANTABILITY Or FITNESS For A PARTICULAR PURPOSE.  See the
;GNU General Public License For more details.
;
;You should have received a copy of the GNU General Public License
;along with this program; if not, write to the Free Software
;Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
;
;Formas de contato :
;Email  : mfdavid@gmail.com ou batchaki@gmail.com
;Cartas : Rua Jubiaba, 110, Vila Madalena. CEP 05444-039, São Paulo, SP - Brasil.
;              ou
;		 Rua Ministro José Galotti, 90, Brooklin. CEP 04580-050, São Paulo, SP - Brasil.
;=======================================================================================

;----------------------------------------------------
; ME_Funcs.bb - Modulo central do Editor de mapas
;----------------------------------------------------
; Looping principal do editor

;Fazendo setup
Include "ME_setup.bb"
;Carregando funções do editor de mapas
Include "ME_Funcs.bb"   ;Funções exclusivas do editor de mapas
Include "ME_GlobalFuncs.bb"
Include "ME_Funcs.bb"
Include "ME_IOFuncs.bb"
;CARREGANDO MAPA Default
LoadMap( "default.map")

;Fazendo looping principal
While Not KeyHit( 1 )
	Cls

	PrintMap()		 			;Desenha o mapa (tiles)
	Textos()
	If f5=0 Then DesenhaLinhas()		;Mostra linhas dos tiles
	TileSel()					;Da um update em qual tile esta selecionado (botao direito do mouse)
	UpdateTileNum()				;Calcula em qual tile esta o mouse (var global tile_num)
	MouseFuncs()				;Faz as funcs do mouse
	CheckTeclado()				;Checa as funções do teclado
	Flip
Wend