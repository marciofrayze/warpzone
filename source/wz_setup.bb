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
; wz_setup.bb - Modulo do Setup
;----------------------------------------------------
; Declaração das variaveis globais
; Alteração da resolução
; Carregando arquivos necessarios, ...

; Alterando resolução e titulo do jogo
Graphics 800, 600, 16, 1
AppTitle "Warp Zone"
SetBuffer BackBuffer()

Print "Loading... Please wait..."
;Carregando funções da Engine de tiles
Include "wz_engine.bb"
;Carregando funções adicionais
Include "wz_funcs.bb"

Global Font1 = LoadFont("Times New Roman",40)
Global Font2 = LoadFont("Times New Roman",20)

FlushJoy
Global x_joyzero#= JoyX()
Global y_joyzero#= JoyY()

;Carregando imagem do player, sons, ...
Global ShipImage = LoadImage("..\arqs\nave.png")
MaskImage ShipImage,255,0,255
ShipFire = LoadAnimImage("..\arqs\fogao.bmp",20,16,0,5)
MaskImage ShipFire,255,0,255
Global explo = LoadAnimImage("..\arqs\explo.png",40,30,0,6)
Global lasersword = LoadSound("..\arqs\lasersword.wav")
Global lasersword2 = LoadSound("..\arqs\lasersword2.wav")
Global menu = LoadImage("..\arqs\menu.png")
frameTimer=CreateTimer(120) ;Para rodar a mesma vel em todos os comps
Global LevelTimer=CreateTimer(1000) ;Para colocar tempo maximo nos leveis
LevelTimer=MilliSecs()
Global timeleft=60 ;60 segundos para acabar o level !
Global fundo = LoadImage ("..\arqs\fundo"+Rand(1,5)+".png")