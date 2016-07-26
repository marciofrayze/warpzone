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
; ME_Funcs.bb - Modulo das funções exclusivas do Editor
;----------------------------------------------------
; Aqui estão as principais função de uso exclusivo do Editor de Mapas (não são usadas nos jogos em si)

Function TileSel()
;Qual tile esta selecionado ?
	If n=1 Then
		If tilesel>0 Then 
			For Tile_Sel.Tiles2 = Each Tiles2
				If Tile_Sel\number = tilesel Then DrawImage Tile_Sel\image , 685,70
			Next				
		EndIf
	EndIf
End Function	


Function UpdateTileNum()		;Calcula em qual tile esta o mouse (var global tile_num)
;Calculando em qual tile esta o mouse
If MouseX()<600 Then
	tile_num = ( MouseX()/20 )
	tile_num = tile_num + ( MouseY()/20 ) * 30
	tile_num = tile_num + 1
EndIf	
End Function 


Function DesenhaLinhas()
Color 130, 130, 130
While ( tile_y < 600 )
	Rect tile_x, tile_y, 20, 20, 0 ; Desenha quadrados para os tiles	
	tile_x = tile_x + 20
	If tile_x = 600 Then
		tile_x = 0
		tile_y = tile_y + 20
	EndIf		
Wend
Color 255, 255, 255
End Function



Function ChooseMap()
	Cls
	FlushKeys ;Tirando bug de aparece "nnnmmnn"
	Flip
	SetBuffer FrontBuffer()
	While temp = 0
		map$ = Input$ ( "Load Map : " )
		Print "Loading... please wait..."
		temp = LoadMap( map$ + ".map")
		If temp = 0 Then Print map$ + " -> File not found ! Press Enter the abort or enter a valid filename."
		If map$ = "" Then temp = 1 ;Aborting
		If temp<>1 Then mapname$ = map$
	Wend		
	SetBuffer BackBuffer() 
	FlushKeys ;Tirando bug de se apertar 2 vezes f1 aparecer a tela 2x
End Function



;Salva o mapa
Function SaveMap()
	Cls
	FlushKeys ;Tirando bug de aparece "nnnmmnn"	
	Flip
	SetBuffer FrontBuffer()
	map$ = Input$ ("Save as : ")
	If map$ = "" Then 
		Print "Not a valid filename ! Press any key to return to Editor."
		WaitKey()
	EndIf
	FlushKeys ;Tirando bug de se apertar 2 vezes f2 aparecer a tela 2x	
	
	;Salvando arquivo
	If map$ <> ""
		fileout = WriteFile ( map$ + ".map" )
		
		;Achando a ID do tile. Ex: wall1.bmp a id é 1
;		For TilesInfo.Tiles = Each Tiles
;			temp=0
;			While ( temp <= num ) 
;				;Para associar um numero a um bmp. Tipo : 1=wall1.bmp
;				If TilesInfo\bmp$ = Tiles$(temp) Then TilesInfo\tile_id = temp
;				temp = temp + 1
;			Wend
;		Next			
			
		
		For TilesInfo.Tiles = Each Tiles
			WriteLine ( fileout, TilesInfo\BMP$ )
		Next
		
		;Gravando tipo de tile
		For TilesInfo.Tiles = Each Tiles
			WriteLine ( fileout, TilesInfo\tile_type )
		Next
			
	CloseFile(fileout)
	EndIf	
	SetBuffer BackBuffer()

	
End Function


Function Textos()
	Color 255, 100, 100
	Text 620, 10, "Warp Zone Map Editor"
	Text 620, 550, "Map Name: " + mapname$ + ".map"
	Color 255, 255, 255
	Text 640, 50, "Selected Tile:"

	If n=1 Then ;Se tiver em modo normal
		Text 620, 150, "Right click to change"
		Text 630, 165, "Left click to apply"
		If tilesel > 0 Then 
			tile_temp$=""
			For Tile_Sel.Tiles2 = Each Tiles2
				If Tile_Sel\number = tilesel Then tile_temp$ = Tile_Sel\BMP$
			Next
			If tile_temp$ <> "" Then 
				temp=LoadImage(tile_temp$)
					DrawImage temp, 685,70 ;Mostrando qual bmp do tile selecionado
			EndIf		
		EndIf			

		;Textos do Normal Mode
		Text 620, 150, "Right click to change"
		Text 630, 165, "Left click to apply"
		Text 610, 250, "F1 to Load Map "
		Text 610, 265, "F2 to Save Map"
		Text 610, 280, "F3 to NEW Map"		
		If F5 = 0 Then 
				Text 610, 295, "F5 to Hide lines"
			Else
				Text 610, 295, "F5 to Show lines"
		EndIf				
		Text 610, 310, "F9 to FILL Map"
		Text 610, 325, "N to Tile Numbers Mode"
		Color 100, 100, 100
		If STT = 0 Then
			Text 610, 340, "M to turn STT ON"
			Else
				Text 610, 340, "M to turn STT OFF"
		EndIf	
		Color 255, 255, 255			

		Text 610, 370, "Tile Number : " + tilenum
		Text 610, 390, "Tile Selected : " + tilesel
		Else ;n=0 (Tile Number mode)
			;Textos do Tile Number Mode
			Text 680, 70, "None"		
			Text 630, 85, "(Tile Number Mode)"
			Text 634, 150, "Left click to add"
			Text 630, 165, "Right click to sub
			Text 610, 250, "F1 to Load Map "
			Text 610, 265, "F2 to Save Map"
			Text 610, 280, "F3 to NEW Map"				
			If F5 = 0 Then 
				Text 610, 295, "F5 to Hide lines"
				Else
					Text 610, 295, "F5 to Show lines"
			EndIf
			Text 610, 325, "N to Normal Mode"	
			Color 100, 100, 100
			Text 610, 310, "F9 to FILL Map"
			Color 255, 255, 255
			If STT = 0 Then
				Text 610, 340, "M to turn STT ON"
				Else
					Text 610, 340, "M turn to STT OFF"
			EndIf	
	
			Text 610, 370, "Tile Number : " + tilenum
			Text 610, 390, "Tile Selected : " + tilesel			
		EndIf
				
	
End Function


Function ClearTiles()
	;Achando qual o tile selecionado
	tile_temp$=""
	For Tile_Sel.Tiles2 = Each Tiles2
		If Tile_Sel\number = tilesel Then tile_temp$ = Tile_Sel\BMP$
	Next

	;Alterando todos os tiles para o tile selecionado !
	For TilesInfo.Tiles = Each Tiles
		TilesInfo\bmp$ = tile_temp$
		TilesInfo\tile_type = 0 ;Setando tile_type para 0
	Next
End Function