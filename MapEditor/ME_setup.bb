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
; ME_setup.bb - Modulo do Setup
;----------------------------------------------------
; Declaração das variaveis globais
; Alteração da resolução
; Carregando arquivos necessarios, ...

Graphics 800, 600, 16, 1
AppTitle "INSANE GAMES - RPG Map Editor"
SetBuffer BackBuffer()

;Declarando variaveis globais
Global tilesel=0 ;numero do tile selecionado 0=nenhum
Global mouse_right=0 ;para tirar bug do mouse
Global mouse_left=0 ;para tirar bug do mouse
Global tile_num = 0 ;Numero do tile em que o mouse esta
Global tile_lines# = 30 ;Numero de tiles por linha
Global tile_col# = 30 ;Numero de linhas
Global tile_width# = 20 ;Width de cada tile
Global tile_heigth# = 20 ;Height de cada tile
Global mapname$="none"

;Essas vars acho q nao precisariam ser globais.. mas.. vao ser ! :P~
Global f5=1 ;0 para mostrar linhas do tile, 1 para nao mostrar.
Global f6=1 ;0 para mostrar tilenum do tile, 1 para nao mostrar.
Global n=1 ; para mostrar Tile Number mode, 1 para Normal Mode
Global STT=0 ;STT (Same Tile Type) altere um tile, mude todos o valor de todos os tiles iguais ! Aperte M no STN
;insertvar=0 ;Aumenta numero de tiles por linha

Type Tiles
	Field image         ;Para usar no comando drawimage tiles\image, x, y
	Field bmp$   	;Nome do bmp do tile
	Field x#    	 	;Posição X do tile
	Field y#		;Posição X do tile
	Field number	;numero do tile (de 1 a 900)
	Field tile_type	;Tile Number (tipo de tile, parede, ..)
	Field tile_id         ;ID. Ex: se bmp$=wall1.bmp entao id=1
End Type

Type Tiles2
	Field image         ;Para usar no comando drawimage tiles\image, x, y
	Field bmp$   	;Nome do bmp do tile
	Field number	;numero do tile (de 1 a 100)
	Field tile_type	;Tile Number (tipo de tile, parede, ..)
	Field tile_id         ;ID. Ex: se bmp$=wall1.bmp entao id=1
End Type

;===============[ CARREGANDO ARQUIVOS ]=================
;Abre arquivo para leitura (nome dos arqs de tiles)
filein = ReadFile("tiles.dat")
Global num=1 ;numero de tiles usado no mousefunc
While ( Not Eof( filein ) ) And ( num < 100 )
	Tile_Sel.Tiles2 = New Tiles2
	Tile_Sel\BMP$=ReadLine$(filein) ;Guarda o tile
	Tile_Sel\number = num 
	Tile_Sel\image = LoadImage ( Tile_Sel\BMP$ )
	num = num + 1
Wend	
CloseFile(filein)

Global warp_x = 0
Global mouse = LoadImage( "mouse.png" ) ;Carregando bmp do mouse

		;Criando TYPES
		For i = 1 To 900
			TilesInfo.Tiles = New Tiles
		Next
		
		
;Colocando includes
Include "ME_GlobalFuncs.bb" ;Funções usadas no editor e nos jogos