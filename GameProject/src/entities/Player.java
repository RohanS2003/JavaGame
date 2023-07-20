package entities;

import static utilz.Constants.PlayerConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 25;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down;
	private float playerSpeed = 2.0f;

	public Player(float x, float y) {
		super(x, y);
		loadAnimations();
	}

	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();
	}

	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, 70, 72, null);
	}

	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}

		}

	}

	private void setAnimation() {
		int startAni = playerAction;

		if (moving)
			playerAction = RUNNING;
		else
			playerAction = IDLE;

		if (attacking)
			playerAction = ATTACK;

		if (startAni != playerAction)
			resetAniTick();
	}

	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		moving = false;

		if (left && !right) {
			x -= playerSpeed;
			moving = true;
		} else if (right && !left) {
			x += playerSpeed;
			moving = true;
		}

		if (up && !down) {
			y -= playerSpeed;
			moving = true;
		} else if (down && !up) {
			y += playerSpeed;
			moving = true;
		}
	}

	private void loadAnimations() {
		InputStream is=getClass().getResourceAsStream("/stationary.png");
		InputStream is1=getClass().getResourceAsStream("/running.png");
		InputStream is2=getClass().getResourceAsStream("/jump.png");
		InputStream is3=getClass().getResourceAsStream("/fall.png");
		InputStream is4=getClass().getResourceAsStream("/attack.png");
		try {
			BufferedImage img = ImageIO.read(is);
			BufferedImage img1=ImageIO.read(is1);
			BufferedImage img2=ImageIO.read(is2);
			BufferedImage img3=ImageIO.read(is3);
			BufferedImage img4=ImageIO.read(is4);

			animations=new BufferedImage[5][6];
			
			animations[0][2]=img.getSubimage(0, 0, 40, 54);
			animations[0][1]=img.getSubimage(41, 0, 40, 54);
			animations[0][0]=img.getSubimage(81, 0, 40, 54);
			
			//run
			animations[1][2]=img1.getSubimage(0, 0, 40, 54);
			animations[1][1]=img1.getSubimage(41, 0, 40, 54);
			animations[1][0]=img1.getSubimage(81, 0, 40, 54);
			
			//fall
			animations[2][3]=img2.getSubimage(0, 0, 40, 54);
			animations[2][2]=img2.getSubimage(41, 0, 40, 54);
			animations[2][1]=img2.getSubimage(81, 0, 40, 54);
			animations[2][0]=img2.getSubimage(120, 0, 40, 54);
			
			//jump
			animations[3][3]=img3.getSubimage(0, 0, 40, 54);
			animations[3][2]=img3.getSubimage(41, 0, 40, 54);
			animations[3][1]=img3.getSubimage(79, 0, 40, 54);
			animations[3][0]=img3.getSubimage(112, 0, 40, 54);
			
			//fight
			animations[4][5]=img4.getSubimage(0, 0, 70, 54);
			animations[4][4]=img4.getSubimage(70, 0, 60, 54);
			animations[4][3]=img4.getSubimage(130, 0, 50, 54);
			animations[4][2]=img4.getSubimage(180, 0, 50, 54);
			animations[4][1]=img4.getSubimage(228, 0, 70, 54);
			animations[4][0]=img4.getSubimage(298, 0, 60, 54);
			//idleAni[0]=img4.getSubimage(240, 0, 60, 54);	

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				is1.close();
				is2.close();
				is3.close();
				is4.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

}