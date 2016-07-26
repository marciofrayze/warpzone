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
; wz_engine.bb - Modulo da engine de tiles
;----------------------------------------------------
; Carrega arquivos necessarios para a engine de tiles
; Cria os Types e inicializa as variaveis necessarias para a engine
; Cria funções da engine (PrintMap, LoadMap, ...)

;Carrega BMP do warp (wz2.bmp)
Global wz2 = LoadAnimImage("..\arqs\wz2.png",20,20,0,7)
MaskImage wz2,255,0,255
Global wz2Timer=CreateTimer(120) ;Para rodar a mesma vel em todos os comps
wz2timer = MilliSecs()
Global wz2Frame=0

Type Tiles
	Field image         ;Para usar no comando drawimage tiles\image, x, y
	Field bmp$   	;Nome do bmp do tile
	Field x#    	 	;Posição X do tile
	Field y#		;Posição X do tile
	Field number	;numero do tile (de 1 a 900)
	Field tile_type	;Tile Number (tipo de tile, parede, ..)
	Field tile_var	;variavel usada nos tiles moveis
End Type

Type Tiles2
	Field image         ;Para usar no comando drawimage tiles\image, x, y
	Field bmp$   	;Nome do bmp do tile
	Field number	;numero do tile (de 1 a 100)
	Field tile_type	;Tile Number (tipo de tile, parede, ..)
End Type

	;Criando TYPES
		For i = 1 To 900
			TilesInfo.Tiles = New Tiles
			TilesInfo\tile_var = 0
		Next
		;Abre arquivo para leitura (nome dos arqs de tiles)
		filein = ReadFile("..\arqs\tiles.dat")
		Global num=1 ;numero de tiles usado no mousefunc
		While ( Not Eof( filein ) ) And ( num < 100 )
			Tile_Sel.Tiles2 = New Tiles2
			Tile_Sel\BMP$=ReadLine$(filein) ;Guarda o tile
			Tile_Sel\number = num 
			Tile_Sel\image = LoadImage ( "..\arqs\"+Tile_Sel\BMP$ )
			num = num + 1
		Wend	
		CloseFile(filein)
		

Function PrintMap( n, pos_x, pos_y )
	;Timer da anim do tile Warp Zone (wz2.bmp)
	If MilliSecs() > wz2Timer + 80 Then
		wz2timer = MilliSecs() ; 'reset' the timer
		wz2Frame = ( wz2Frame + 1 ) Mod 7 ; increment the Frame, flip to 0 if we are out
	End If 
	
	;Colocando os tiles normais
	For TilesInfo.Tiles = Each Tiles
		;Procura por qual tile (Tile_Sel) corresponde o BMP$ e desenha a imagem	
		temp = 0
		For Tile_Sel.Tiles2 = Each Tiles2
			If Tile_Sel\BMP$ = TilesInfo\BMP$ Then 
				temp = Tile_Sel\image
				If Tile_Sel\BMP$="wz.png" Then temp=-1 ;indica q é o WZ (pra coloca anim)
				Goto fim
			EndIf
		Next
		.fim
		;Desenhando o tile
		If temp > 0 Then  DrawImage temp, TilesInfo\x#+pos_x , TilesInfo\y# + pos_y     ;Desenha tile
		If temp = -1 Then DrawImage wz2, TilesInfo\x#+pos_x , TilesInfo\y# + pos_y, wz2Frame ;Desenha o warp zone
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
			If bmp_x# = 600 Then
				bmp_x# = 0
				bmp_y# = bmp_y# + 20
			EndIf
		Next
	EndIf
	
	Return filein
End Function

;Checa se a imagem img esta colidindo com um tile do tipo tile_type
Function TileCollision( img, img_x#, img_y#, tile_type )
temp = 0 ;0 - nao tem colisao. 1 - tem colisao

	For TilesInfo.Tiles = Each Tiles
	
		If (TilesInfo\x#>=((x# - 59/2)-20) And TilesInfo\x#<=((x# - 59/2)+player_tx+20) And TilesInfo\y#>=(y# - 20) And TilesInfo\y#<= (y# + player_ty + 20))

			If TilesInfo\tile_type = tile_type Then
				;Acha qual a imagem do tile !
				For TilesInfo2.Tiles2 = Each Tiles2
					If TilesInfo2\bmp$ = TilesInfo\bmp$ Then 
						tempB = TilesInfo2\image
						Goto fim ;Aumenta a vel de acesso
					EndIf					
				Next					
			EndIf
			
		EndIf
	Next		
	.fim		
Return temp
End Function


;Checa se a imagem img esta colidindo com um tile do tipo tile_type
Function TileCollision2( img, img_x#, img_y#)
temp = 0 ;Retorna qual o tile_type q esta colidindo

	For TilesInfo.Tiles = Each Tiles
		If TilesInfo\tile_type > 0
			temp = TilesInfo\tile_type
			;Acha qual a imagem do tile !
			For TilesInfo2.Tiles2 = Each Tiles2
				If TilesInfo2\bmp$ = TilesInfo\bmp$ Then 
					tempB = TilesInfo2\image
					Goto fim ;Aumenta a vel de acesso
				EndIf					
			Next					
			.fim
;			If ImagesOverlap (img, img_x#, img_y#, 0, tempB, TilesInfo\X#, TilesInfo\Y#,0) Then 
			If ImagesCollide (img, img_x#, img_y#,0, tempB, TilesInfo\X#, TilesInfo\Y#,0) Then 

				Goto fim2
				Else
					temp = 0
			EndIf
	
		EndIf
	Next		
	.fim2					
Return temp
End Function


;Checa se a imagem img esta colidindo com um tile do tipo tile_type
;Igual a 2, mas com sistema de colisao melhor !
Function TileCollision2cu( img, img_x#, img_y#)
temp = 0 ;Retorna qual o tile_type q esta colidindo
	For TilesInfo.Tiles = Each Tiles
		If ( TilesInfo\tile_type > 0) Then
			If y# + 35 > TilesInfo\y# And y# < TilesInfo\y# +20 Then
				If x# + 35 > TilesInfo\x# And x# < TilesInfo\x# + 20 Then temp=TilesInfo\tile_type
			EndIf				
		EndIf
	Next		
Return temp
End Function