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
; ME_cd.bb - Modulo das funções de Collision Detect
;----------------------------------------------------
;Funções para facilitar a CD

;Verifica a colisao entre uma imagem e retornar em que tipo de tile ele esta colidindo
Function TileCollision1( img, img_x#, img_y#, tile_type )
temp = 0 ;0 - nao tem colisao. 1 - tem colisao

	For TilesInfo.Tiles = Each Tiles
		If TilesInfo\tile_type = tile_type Then
			;Acha qual a imagem do tile !
			For TilesInfo2.Tiles2 = Each Tiles2
				If TilesInfo2\bmp$ = TilesInfo\bmp$ Then 
					tempB = TilesInfo2\image
					Goto fim ;Aumenta a vel de acesso
				EndIf					
			Next					
		EndIf
	Next		
	.fim
Return temp
End Function