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
; ME_GlobalFuncs.bb - Modulo das funções principais
;----------------------------------------------------
; Aqui estão as funções principais, que serão usadas tanto no editor quanto nos jogos
; Funções como : LoadMap, PrintMap, ...


Function PrintMap()
;Colocando os tiles normais
	For TilesInfo.Tiles = Each Tiles
		;Procura por qual tile (Tile_Sel) corresponde o BMP$ e desenha a imagem	
		temp = 0
		For Tile_Sel.Tiles2 = Each Tiles2
			If Tile_Sel\BMP$ = TilesInfo\BMP$ Then temp = Tile_Sel\image
		Next
		;Desenhando o tile
		If temp <> 0 Then DrawImage temp, TilesInfo\x# , TilesInfo\y#      ;Desenha tile
		If n=0 Then Text TilesInfo\x# + 6 , TilesInfo\y# + 3, TilesInfo\tile_type
	Next
End Function

Function LoadMap( map$)
	;Abre arquivo para leitura (mapa)
	filein = ReadFile( map$ )
	If filein<>0 ;Se existir o arq

		i=0
		For TilesInfo.Tiles = Each Tiles
			i = i + 1
			TilesInfo\bmp$ = ReadLine$( filein )
;			TilesInfo\image = LoadImage (TilesInfo\bmp$)
			TilesInfo\number = i
;			DebugLog TilesInfo\image
		Next

		For TilesInfo.Tiles = Each Tiles
			 TilesInfo\tile_type = ReadLine$( filein )
		Next

	CloseFile(filein)


	;Informações sobre os tiles !
		bmp_x# = 0
		bmp_y# = 0
		For TilesInfo.Tiles = Each Tiles
			TilesInfo\x# = bmp_x#
			TilesInfo\y# = bmp_y#
			
;			bmp_x# = bmp_x# + tile_width
			bmp_x# = bmp_x# + 20
			If bmp_x# = (tile_width# * tile_lines#) Then
;			If bmp_x# = 600 Then
				bmp_x# = 0
;				bmp_y# = bmp_y# + tile_heigth
				bmp_y# = bmp_y# + 20
			EndIf
		Next
	EndIf
	
	Return filein
End Function