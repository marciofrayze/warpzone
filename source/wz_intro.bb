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
; wz_intro.bb - Modulo da introdução
;----------------------------------------------------
; Inicializa variaveis/imagens e musica da introdução
; Faz toda a introdução inicial
; Tira as imagens utilizadas na introdução da memoria

;Graphics 800,600			
								; or maybe 24, which should look the same as 32 since its a 24bit bitmap.
;SetBuffer BackBuffer()
Global splhoriz=LoadAnimImage("..\arqs\wzlogo.png",800,1,0,600)
Global scroll_y=0
Global backdrop=LoadImage("..\arqs\wzback.png")
Global chnBackground=PlayMusic("..\arqs\batcha.mp3")

Global x_intro = 305
Global y_intro = 800

;ClsColor 255,255,255 ;CLS branco

;PARTE DO TEXTO BOUCING
Global count=0
Global lineoftext$=" Insane Games....... Presents....... Warp Zone ! ! ! ! "+String(".",20)+" Press SPACE to start"+String(".",20)+" Programming : Márcio Frayze David (mfdavid@gmail.com)....... Graphics and Music : Marcelo Baccelli (batchaki@gmail.com) "+String(".",20)+" Thanks To : Mark Sibly, Jonathan Pittock, Blitz Basic forum, and all my friends ! =D "+String(".",20)+" Insane Game official site : www.insanegames.tk "+String(".",20)

Global font=LoadFont("arial",55,True,False,False)
Global getletterx=1

;Include "modulos/rasterbars.bb"

Type objects
Field x
Field y
Field letter
End Type

Dim sintable(GraphicsWidth())
For i=0 To GraphicsWidth()
sintable(i)=Sin(i)*40
Next

;Dim fonttable(127)
Dim fonttable(900)
SetFont(font)
For i=0 To 900
fonttable(i)=CreateImage(StringWidth(Chr$(i)),StringHeight(Chr$(i)))
SetBuffer ImageBuffer(fonttable(i))
Color 0,0,0
Rect 0,0,StringWidth(Chr$(i)),StringHeight(Chr$(i))
Color 255,255,255
Text 0,0,Chr$(i)
;MaskImage fonttable(i),200,200,200
Next
;FIM DO TEXTO BOUCING SETUP

;Variaveis para variar o fundo da intro
Global IntroTimer=CreateTimer(1000) ;Para colocar tempo maximo nos leveis
IntroTimer=MilliSecs()
Global Introfundo1 = LoadImage ("..\arqs\screenshot1.png")
Global Introfundo2 = LoadImage ("..\arqs\screenshot2.png")
Global Introfundo3 = LoadImage ("..\arqs\screenshot3.png")
Global IntroFundo0 = LoadAnimImage("..\arqs\wzlogo.png",800,1,0,600)
Global IntroFundo

;Dim xpos#(600)
;Dim xmov#(600)
Dim ypos#(600)
;Dim ymov#(600)

;FADE_ProjectXDown(1)			; A Project X Fade Down the screen.
;FADE_ProjectXUp(1,4)			; A Project X Fade Up the screen.
;FADE_BounceHoriz(0,2,4)			; Fly-By full image.
FADE_BounceHoriz(80,3,3)		; Fly-By Distort
;FADE_BounceHoriz(360,2,2)		; Fly-By eratic.

;End

ClsColor 0,0,0 ;voltando para o CLS normal (preto)
FreeImage splhoriz
FreeImage wzlogo
FreeImage backdrop

;########## FADE ROUTINES ###########

Function FADE_ProjectXDown(speedy)
	movey=0
	Repeat
;		Cls
		If movey<600
			For a=0 To 599
				If a<movey
					DrawImage splhoriz,0,a,a
				Else
					DrawImage splhoriz,0,a,movey
				EndIf
			Next
			movey=movey+speedy
		Else
			For a=0 To 599
				DrawImage splhoriz,0,a,a
			Next
		EndIf
		If KeyHit(1) Then End
		Flip
	Until MouseHit(1)
End Function

Function FADE_ProjectXUp(speedy,speedys)
	movey=600
	moveys=600
	Repeat
		Cls
		If movey>0
			For a=0 To 599
				If a<movey
					DrawImage splhoriz,0,a,a
				Else
					DrawImage splhoriz,0,a,movey
				EndIf
			Next
			movey=movey-speedy
		Else
			If moveys>0
				For a=0 To 599
					If a<moveys
						DrawImage splhoriz,0,a,0
					EndIf
				Next
				moveys=moveys-speedys
			EndIf
		EndIf
		If KeyHit(1) Then End
		Flip
	Until MouseHit(1)
End Function

Function FADE_BounceHoriz(blur,speed#,fadeduration#)
	rot#=360
	mnt#=600
	For a=1 To 599
		ypos(a)=Rnd(0.0,blur)
	Next
	Repeat
		SetBuffer BackBuffer()
		Cls
	
;		If stopit=True
			;fundo com scroll
;			TileBlock backdrop,0,scroll_y
			;scroll the backdrop
;			scroll_y=scroll_y+1
;			If scroll_y=ImageHeight(backdrop) Then scroll_y=0
;		EndIf			

		If stopit=False
			For a=0 To 599
				inv#=(Cos(rot+ypos(a))*mnt)
				DrawImage splhoriz,inv,a,a
			Next
			mnt=mnt-fadeduration#
		Else
			For a=0 To 599
				If IntroFundo=0 Then DrawImage splhoriz,0,a,a
			Next
		EndIf
		If mnt<0
						If IntroFundo=1 Then DrawImage IntroFundo1,0,0
						If IntroFundo=2 Then DrawImage IntroFundo2,0,0
						If IntroFundo=3 Then DrawImage IntroFundo3,0,0

			stopit=True
		Else
			rot=rot-speed
		EndIf
		If KeyHit(1) Then End




If stopit=True Then
;TEXTO BOUCING
For ob.objects=Each objects
ob\x=ob\x-2
If ob\x<0 Then Delete ob.objects
Next

If count=0 Then
count=15
ob.objects=New objects
ob\x=GraphicsWidth()
ob\y=350
ob\letter=Asc(Mid$(lineoftext$,getletterx,1))
getletterx=getletterx+1
If getletterx>Len(lineoftext$) Then getletterx=1
Else
count=count-1
End If
	For ob.objects = Each objects
		DrawImage fonttable(ob\letter),ob\x-(ImageWidth(fonttable(ob\letter))/2),ob\y+sintable(ob\x)-(ImageHeight(fonttable(ob\letter))/2)
	Next

	
;FREEWARE BOUCING
If y_intro<50 Then 
	Color (y_intro*3)+100,(y_intro*3)+100,(y_intro*3)+100
	Text x_intro,y_intro + 480,"Open Source"
	Else
		Color ((100-y_intro)*3)+100,((100-y_intro)*3)+100,((100-y_intro)*3)+100
		Text x_intro,100-y_intro + 480,"Open Source"
EndIf
y_intro = ( y_intro + 1 ) Mod 100

MudaFundo()
CheckMusic() ;faz o looping do mp3
EndIf ;Termina oq tem q mostrar depois q tiver parado

	Flip
	Until (KeyHit (57) Or JoyDown(1))
End Function

Function MudaFundo()
	If MilliSecs() > (IntroTimer + 10000) Then
		IntroTimer=MilliSecs() ; 'reseta' o timer
		IntroFundo=Rand(0,3) ;Seleciona um fundo aleatoriamente
	End If 
;	If IntroFundo = 1 Then splhoriz = IntroFundo1
;	If IntroFundo = 2 Then splhoriz = IntroFundo2
;	If IntroFundo = 3 Then splhoriz = IntroFundo3
End Function