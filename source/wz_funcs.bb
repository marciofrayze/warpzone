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
; wz_funcs.bb - Modulo das funções do WZ
;----------------------------------------------------
; Cria funções adicionais do WZ
; Este modulo deveria esta separado em mais arquivos...

Function ScreenShot()
	If KeyHit(59) Then
		SaveBuffer(FrontBuffer(),"screenshot.bmp")
	End If
End Function

Function MoveShip()
fire=0
	If KeyDown (200) Or y_joyzero#-JoyY()>0 Or JoyDown(1) Then 
		acel_y# = acel_y# - 0.03
		fire=1
	
		Else
			If acel_y# < 0 Then acel_y# = acel_y# + 0.01
	EndIf	

	If KeyDown (205) Or x_joyzero#-JoyX()<0 Then 
		acel_x# = acel_x# + 0.02
		fire=1
		Else
			If acel_x# > 0 Then acel_x# = acel_x# - 0.01
	EndIf	
	
	If KeyDown (203) Or x_joyzero#-JoyX()>0 Then 
		acel_x# = acel_x# - 0.02
		fire=1
		Else
			If acel_x# < 0 Then acel_x# = acel_x# + 0.01
	EndIf	
	acel_y = acel_y + 0.01 ;Gravidade
	x# = x# + acel_x#
	y# = y# + acel_y#	
End Function


Function NextLevel()

	PlaySound lasersword
	For fade_temp=1 To 600/8 + 5
		CheckEsc()
		If gameover=1 Then Goto fimjogo ;Se apertar esc parar
		Cls
		DrawImage fundo, 0, fade_temp*8 ;coloca o png de fundo
		PrintMap(1, 0, fade_temp*8)
		DrawImage menu, 600, 0 ;coloca o png do menu a direita
		Textos()
		Flip
	Next		
	PlaySound lasersword2

	FreeImage fundo
	fundo = LoadImage ("..\arqs\fundo"+Rand(1,5)+".png")
	fire=0
	level = level + 1
	If level > ultimolevel Then level = 1
	temp$=level
	LoadMap("..\maps\" + temp$ + ".map")
	ZeraVars()
	

	For fade_temp=1 To 600/8
		CheckEsc()
		If gameover=1 Then Goto fimjogo ;Se apertar esc parar
		Cls
		DrawImage fundo, 0, 600-fade_temp*8 ;coloca o png de fundo
		PrintMap(1, 0, 600 - fade_temp*8)
		DrawImage menu, 600, 0 ;coloca o png do menu a direita
		Textos()
		Flip
	Next	

	.fimjogo
	FlushKeys()
End Function

Function startlevel()
;Set the Font
SetFont(Font1)

	If vidas>0 And gameover=0 Then ;Tira o bug de aparece "Level X" quando vc morre (ultima vida)
		Text 265, YLocation+40, "Level "+level ; Draw the Text
		If YLocation > 0 Then       				; If Y Location of text is equal to or greater than 0
			YLocation = YLocation - 5 ; Move the text up 2 pixels
			Else
				Color (255,255,255) 
				death = 0
				YLocation = 480
				SetFont(Font2)
		EndIf	
		Else
			death = 0
	EndIf	
End Function


Function Textos()
	SetFont(Font2)
	Color 0, 0, 120
	Text 693, 244, level
	Text 730, 280,timeleft
;	Text 730, 420,wz2Frame
	If vidas > 0 Then 
		Text 733, 315, vidas
		Else
			Text 733, 315, "0"
	EndIf			
;Colocando a cor certa para a funcao startlevel()
If YLocation  < 255 Then 
	Color (YLocation,YLocation,YLocation)	; set the fade color of the text
	Else
		Color (255,255,255) 
EndIf			
	
End Function

Function Intro()
	Cls
	wzlogo = LoadImage("..\arqs\wzlogo.png")
;	MaskImage wzlogo,255,255,255
	scroll_y=0
	backdrop=LoadImage("..\arqs\wzback.png")
	chnBackground=PlayMusic("..\arqs\after_forever.mid")
	Flip
	Cls

;loop until ESC hit
ClsColor 255,255,255
While Not value
	Cls
	value=GetKey()
	DrawImage wzlogo,100, 0
	Flip
Wend
ClsColor 0,0,0 ;voltando a cor normal do CLS
End Function

Function Explo()
	If MilliSecs() > tmrFrame + 100 Then
			tmrFrame=MilliSecs() ; 'reset' the timer
		Frame=( Frame + 1 ) Mod 6 ; increment the Frame, flip to 0 if we are out
	End If 
	DrawImage explo, x#, y#, Frame
End Function

Function Death()
vidas = vidas - 1
FlushKeys()
FlushJoy
	While Not (KeyHit(57) Or JoyHit(1)) Or gameover = 1
		Cls
		DrawImage fundo,0,0 ;coloca o png de fundo
		explo()
		PrintMap(1,0,0)
		DrawImage menu, 600, 0 ;coloca o png do menu a direita
		If vidas > -1 Then 
			Text 230,120,"Press SPACE to try again !"
			Else
				Text 250,100,"GAME OVER"
				Text 230,140,"Thanks for playing !"
		EndIf				
		Textos()
		CheckEsc()
		ScreenShot() ;Aperte F1 para tirar um screenshot (screenshot.bmp)
		Flip
	Wend
If vidas = -1 Then gameover = 1
ZeraVars()
FlushKeys()
startlevel()
End Function	

Function ZeraVars()
	x#=20
	y#=30	
	acel_x#=0
	acel_y#=0
	death=-1
	timeleft=60
End Function

Function Flash() ;Altera a variavel Flash=0 (Eveito para piscar a tela) - usado no level 10
	If MilliSecs() > tmrFlash + 1000 Then
		tmrFlash=MilliSecs() ; 'reset' the timer
		Flash=( Flash + 1 ) Mod 2 ; increment the Frame, flip to 0 if we are out
	End If 
End Function

Function leveltimer()
	If MilliSecs() > LevelTimer + 1000 And death<>-1 Then
		LevelTimer=MilliSecs() ; 'reset' the timer
		timeleft=timeleft - 1 ; increment the Frame, flip to 0 if we are out
		If timeleft=0 Then timeover()
	End If
End Function

Function timeover()
vidas = vidas - 1
If vidas = -1 Then gameover = 1
FlushKeys() 
FlushJoy
	While Not (KeyHit(57) Or JoyHit(1)) Or gameover = 1
		Cls
		DrawImage fundo,0,0 ;coloca o png de fundo
		DrawImage shipimage,x#,y# ;desenha a nave
		PrintMap(1,0,0)
		DrawImage menu, 600, 0 ;coloca o png do menu a direita
		If vidas > -1 Then 
			Text 250,100,"TIME OVER !"
			Text 210,120,"Press SPACE to try again !"
			Else
				Text 250,80,"TIME OVER !"
				Text 250,100,"GAME OVER"
				Text 230,140,"Thanks for playing !"
		EndIf				
		Textos()
		CheckEsc()
		Flip
	Wend

ZeraVars()
FlushKeys()
startlevel()
End Function

Function CheckEsc()
;Checa se apertou esc durante o jogo. Se apertar, volta para tela inicial
	If KeyHit(1) Then gameover=1
End Function

Function CheckMusic()
;Checa se a musica parou. Se parou, tocar denovo !!
	If Not ChannelPlaying(chnBackground) Then chnBackground=PlayMusic("..\arqs\batcha.mp3")
End Function