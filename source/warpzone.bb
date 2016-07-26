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
; warpzone.bb - Modulo principal
;----------------------------------------------------
; Declaração de variaveis adicionais
; Looping principal

;Inicializando variaveis globais, mudando resolução, ...
Include "wz_setup.bb"

.start ;Zera todas as variaveis !
Global x#=20
Global acel_x#=0
Global y#=20
Global acel_y#=0
Global YLocation = 480
Global Death=-1 ;ainda nao começou
Global fire=0 ; 0-nao acelerando, 1-acelerando
Global Frame=0
Global tmrFrame 
Global Flash=0 ;Efeito para piscar a tela
Global tmrFlash=0 ;Efeito para piscar a tela (timer)
Global Vidas=10

Global level = 1
Global ultimolevel = 16
Global gameover=0

temp$=level
LoadMap("..\maps\" + temp$ + ".map")

;Intro()
;Mostrando o png inicial e fazendo o efeito da entrada
Include "wz_intro.bb"
zeravars()
;============================ INICIO DO MAIN LOOP ============================
While Not KeyHit(1) Or gameover=1
	FlushJoy ;tira o bug do joy do batcha
	WaitTimer(frameTimer) ;Utilizado para rodar a mesma velocidade em todos os comps

	Cls
	If level=10 Then ;Esses leveis terão o mapa piscando
		Flash()
		Else
		flash=0
	EndIf		

	;Desenhando o jogo :
	DrawImage fundo,0,0 ;coloca o png de fundo
	If fire=1 Then 
		DrawImage ShipFire, x#+7.5, y#+25, Frame 
	EndIf
	If Flash=0 Then PrintMap(1, 0, 0) ;Printa o mapa sem debug
	DrawImage menu, 600, 0 ;coloca o png do menu a direita
	If death<>1 DrawImage ShipImage, x#, y# ;Coloca nave do player	
	If death=0 Then MoveShip() ;Move a nave com as setas e coloca gravidade
	
	cdtemp = 0
	cdtemp = TileCollision2( ShipImage, x#, y#) ;Verifica com que tipo de tile a nave esta colidindo
	If cdtemp = 1 Then ;Parede
		death = 1
		fire=0
		Death()
	EndIf		
	If cdtemp = 2 Then NextLevel() ;Warp Zone normal
	If cdtemp = 3 Then ;Warp tipo 3. Vai passar 2 leveis ao inves de 1 !
		level = level + 1 
		NextLevel()		
		EndIf
	If cdtemp = 4 Then ;Warp tipo 4. Volta um nivel !
		level = level - 2 
		NextLevel()		
		EndIf	
	If cdtemp = 9 Then ;Pegou uma vida ! (so pode ter UMA por fase !!!)
		For TilesInfo.Tiles = Each Tiles
			If TilesInfo\BMP$ = "heart.png" Then 
				TilesInfo\BMP$ = ""
				vidas=vidas+1
				TilesInfo\tile_type = 0
			EndIf
		Next
	EndIf	
			
	
	If MilliSecs() > tmrFrame + 100 Then
		tmrFrame=MilliSecs() ; 'reseta' o timer
		Frame=( Frame + 1 ) Mod 2 ;Altera qual o frame (adiciona 1 ou volta pra 0)
	End If 

	If death =-1 Then startlevel() ;Faz o texto "Level X" aparecer

	Textos() ;Mostra os textos a direita

	leveltimer() ;Da um update nas variaveis de timer do level

	CheckEsc() ;Verifica se o player apertou ESC
	CheckMusic() ;Faz o looping do mp3
	ScreenShot() ;Aperte F1 para tirar um screenshot (screenshot.bmp)
	Flip	
Wend
;============================ FIM DO MAIN LOOP ============================

If gameover=1 Then Goto start ;Volta pro menu inicial