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
; ME_IOFuncs.bb - Modulo das funções de I/O do Editor de Mapas
;----------------------------------------------------
; Aqui estão as principais função de I/O de uso exclusivo do Editor de Mapas (não são usadas nos jogos em si)

Function MouseFuncs()
	;Colocando Simbolo do mouse
	MaskImage mouse, 0, 255, 0 
	DrawImage mouse, MouseX(), MouseY()

	;Selecionando tile com botao direito do mouse
	If (MouseDown(2) And mouse_right=0 And n=1)Then
		mouse_right=1
		tilesel = tilesel + 1
		If tilesel = num Then 
			tilesel = 0
		EndIf	
	EndIf
	If (Not (MouseDown(2) ) And mouse_right=1) Then mouse_right=0
	
	;Mudando o tile (botao eskerdo do mouse) - so muda se estiver em normal mode ( n = 1 )
	If ( MouseDown( 1 ) And n = 1) Then 
		;Acharno Tile_Sel qual o BMP da posicao tilesel do tiles.dat
		;Achar qual o tile selecionado no TilesInfo e alterar para o BMP achado, e carregar imagem	
		For Tile_Sel.Tiles2 = Each Tiles2 ;Achando qual o BMP do tile tilesel
			If Tile_Sel\number = tilesel Then tile_temp$ = Tile_Sel\BMP$
		Next			
		For TilesInfo.Tiles = Each Tiles ;Achando o tile a ser alterado e altera-lo !
			If TilesInfo\number = tile_num Then
				TilesInfo\bmp$ = tile_temp$
				TilesInfo\image = ""
				If TilesInfo\bmp$ <> "" Then TilesInfo\image = LoadImage (TilesInfo\bmp$)
			EndIf				
		Next			
	EndIf	
;Mudando o numero do tile (botao eskerdo do mouse) - so muda se estiver em Number Tile Mode ( n = 0 )
	If ( MouseDown( 1 ) And n = 0 And mouse_left=0) Then 
		If STT = 0 ;Se o STT (Same Tile Type) estiver desligado :
			For TilesInfo.Tiles = Each Tiles
				If TilesInfo\number = tile_num Then 
					temp = TilesInfo\tile_type
					If temp + 1 <=9 Then 
						TilesInfo\tile_type = temp + 1
						Else
							TilesInfo\tile_type = 0
					EndIf				
				EndIf				
			Next
		Else ;Se o STT (Same Tile Type) estiver ligado :
			;Achando  o tipo de tile (BMP) q o usuario selecionou (coloca na var tile_temp)
			For TilesInfo.Tiles = Each Tiles	
				If TilesInfo\number = tile_num Then 
					tile_temp$ = TilesInfo\BMP$
					type_temp = TilesInfo\tile_type
						If type_temp + 1 <=9 Then 
							TilesInfo\tile_type = type_temp + 1
							Else
								TilesInfo\tile_type = 0
						EndIf								
				;Guarda qual o valor para usar nos outros tiles
				tile_type = 	TilesInfo\tile_type
				EndIf						
			Next	
			
			;Mudando todos os tiles do mesmo tipo !! (mesmo Tile/BMP da var tile_temp)
			For TilesInfo.Tiles = Each Tiles
				If TilesInfo\bmp$ = tile_temp$ Then TilesInfo\tile_type = tile_type
			Next
	EndIf			
					

	mouse_left=1
	EndIf	

	If (Not (MouseDown(1) ) And mouse_left=1) Then mouse_left=0


;Mudando o numero do tile (botao eskerdo do mouse) - so muda se estiver em Number Tile Mode ( n = 0 )
	If ( MouseDown( 2 ) And n = 0 And mouse_right=0) Then 
		mouse_right=1		
		If STT = 0 ;Se o STT (Same Tile Type) estiver desligado :
			For TilesInfo.Tiles = Each Tiles
				If TilesInfo\number = tile_num Then 
					temp = TilesInfo\tile_type
					If temp - 1 >= 0 Then 
						TilesInfo\tile_type = temp - 1
						Else
							TilesInfo\tile_type = 9
					EndIf				
				EndIf				
			Next
		Else ;Se o STT (Same Tile Type) estiver ligado :
			;Achando  o tipo de tile (BMP) q o usuario selecionou (coloca na var tile_temp)
			For TilesInfo.Tiles = Each Tiles	
				If TilesInfo\number = tile_num Then 
					tile_temp$ = TilesInfo\BMP$
					type_temp = TilesInfo\tile_type
						If type_temp - 1 >= 0 Then 
							TilesInfo\tile_type = type_temp - 1
							Else
								TilesInfo\tile_type = 9
						EndIf								
				;Guarda qual o valor para usar nos outros tiles
				tile_type = 	TilesInfo\tile_type
				EndIf						
			Next	
			
			;Mudando todos os tiles do mesmo tipo !! (mesmo Tile/BMP da var tile_temp)
			For TilesInfo.Tiles = Each Tiles
				If TilesInfo\bmp$ = tile_temp$ Then TilesInfo\tile_type = tile_type
			Next
		EndIf			
	EndIf 
End Function


Function CheckTeclado()
	; ================[ Fs, N e M ]================
	;Podendo apertar M (STT)
	If KeyHit(50) Then
		If n = 0 Then ;so no Tile Number Mode
			If STT = 0 Then 
				STT = 1
				Else
					STT = 0
			EndIf
		EndIf		
	EndIf	

	;Podendo apertar N (Tile number mode !)
	If KeyHit(49) Then
		If n=0 Then 
			n=1
			Else
				n = 0
		EndIf
	EndIf	

	;Podendo apertar F9 - Fill (limpa tela com tile selecionado)
	If KeyHit(67) Then
		ClearTiles()
	EndIf

	;Podendo apertar F5 - mostra linhas dos tiles
	If KeyHit(63) Then
		If f5=0 Then 
			f5=1
			Else
				f5=0
		EndIf
	EndIf		

	;F1 - Load Map
	If KeyHit(59) Then
		ChooseMap()
	EndIf
	;F2 - Save Map
	If KeyHit(60) Then
		SaveMap()
	EndIf
End Function