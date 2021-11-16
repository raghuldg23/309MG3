package com.mg3.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mg3.game.MG3game;



public class WelcomePage implements Screen {


    Skin skin;
    Skin skinInputBox;
	
    MG3game game;
    TextField IGN;
    TextField IPAddress;
    private Texture welcomeTexture;
    Texture TitleName;
    SpriteBatch batch;
    private Stage stage;
    private Music BGM2;


    private float deltaSum;

    public WelcomePage(MG3game game) {
        this.game = game;
        TitleName = new Texture(Gdx.files.internal("mg32dbattle.png"));
		BGM2 = Gdx.audio.newMusic(Gdx.files.internal("Touhou 10.5 - Song 01 - Main Menu Theme - Sky of Scarlet Perception.mp3"));
        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(1280, 720));
        
        Gdx.input.setInputProcessor(stage);
        createBasicSkin();
        
		BGM2.setVolume(0.05f);
		BGM2.setLooping(true);
		BGM2.play();
        
        TextButton newGameButton = new TextButton("Join game", skin); 
        newGameButton.setPosition(Gdx.graphics.getWidth()/2- Gdx.graphics.getWidth()/8  , Gdx.graphics.getHeight()/2+ Gdx.graphics.getHeight()/6);
        stage.addActor(newGameButton);
        
        TextButton exitButton = new TextButton("Exit", skin); 
        exitButton.setPosition(Gdx.graphics.getWidth()/2- Gdx.graphics.getWidth()/8  , Gdx.graphics.getHeight()/2- Gdx.graphics.getHeight()/6);
        stage.addActor(exitButton);
               
        IGN = new TextField ("Username", skin);
        IGN.setPosition(540, 400);
        IGN.setSize(200, 30);
        stage.addActor(IGN);
        IPAddress = new TextField ("IP Address", skin);
        IPAddress.setPosition(540, 350);
        IPAddress.setSize(200, 30);
        stage.addActor(IPAddress);
        }
    
    private void createBasicSkin(){
    	  //Create a font
    	  BitmapFont font = new BitmapFont();
    	  skin = new Skin();
    	  skin.add("default", font);

    	  //Create a texture
    	  Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
    	  pixmap.setColor(Color.WHITE);
    	  pixmap.fill();
    	  skin.add("background",new Texture(pixmap));

    	  //Create a button style
    	  TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
    	  textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
    	  textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
    	  textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
    	  textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
    	  textButtonStyle.font = skin.getFont("default");
    	  skin.add("default", textButtonStyle);
    	  
    	  Pixmap textBG = new Pixmap(200,300, Pixmap.Format.RGB888);
    	  textBG.setColor(Color.LIGHT_GRAY);
    	  textBG.fill();
    	  TextureRegionDrawable textBGDraw = new TextureRegionDrawable(new TextureRegion(new Texture(textBG)));
    	  
    	  Pixmap curserTex = new Pixmap(2,20, Pixmap.Format.RGB888);
    	  curserTex.setColor(Color.BLACK);
    	  curserTex.fill();
    	  TextureRegionDrawable curserTexDraw = new TextureRegionDrawable(new TextureRegion(new Texture(curserTex)));
    	  
    	  TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
    	  textFieldStyle.background = textBGDraw;
    	  textFieldStyle.cursor = curserTexDraw;
    	  textFieldStyle.font = font;
    	  textFieldStyle.fontColor = Color.DARK_GRAY;
    	  textFieldStyle.focusedFontColor = Color.BLACK;
    	  skin.add("default", textFieldStyle);
    	}

    @Override
    public void show() {
        deltaSum = 0;
    }

    @Override
    public void render(float delta) {
        deltaSum += delta;
        
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
      //play button click  
        if((Gdx.input.getX()>(Gdx.graphics.getWidth()/2- Gdx.graphics.getWidth()/8)) && (Gdx.input.getX()<(Gdx.graphics.getWidth()/2+ Gdx.graphics.getWidth()/8)) 
            	&& (Gdx.input.getY() > (Gdx.graphics.getHeight()/2- Gdx.graphics.getHeight()/6- Gdx.graphics.getHeight()/10) )&& (Gdx.input.getY() < (Gdx.graphics.getHeight()/2- Gdx.graphics.getHeight()/6))) {
            	if(Gdx.input.isTouched()) {
            		BGM2.dispose();
            		game.serverIP = IPAddress.getText();
            	 	game.playerName = IGN.getText();
            	 	game.showGameScreen();
            	} 	
        }

        
      //exit click
        if((Gdx.input.getX()>(Gdx.graphics.getWidth()/2- Gdx.graphics.getWidth()/8)) && (Gdx.input.getX()<(Gdx.graphics.getWidth()/2+ Gdx.graphics.getWidth()/8)) 
            	&& (Gdx.input.getY() > (Gdx.graphics.getHeight()/2 + Gdx.graphics.getHeight()/6-Gdx.graphics.getHeight()/10) )&& (Gdx.input.getY() < (Gdx.graphics.getHeight()/2+ Gdx.graphics.getHeight()/6))) {
            	if(Gdx.input.isTouched()) {
            	Gdx.app.exit();} 	
            }
        
        batch.begin();
        batch.draw(TitleName, 50, 575,1206,155); //1280, 300);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {

        if (stage != null) {
            stage.dispose();
        }
        if (welcomeTexture != null) {
            welcomeTexture.dispose();
        }
    }


}
