package objects.handlers;

import game.CameraID;
import game.Game;
import objects.misc.Camera;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class MouseHandler extends MouseAdapter {
    private boolean mouseDown;
    private Point2D.Double mousePos = new Point2D.Double();
    private Game game;

    public MouseHandler(Game game) {
        super();
        this.game = game;
        mouseDown = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        mouseDown = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        mouseDown = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        super.mouseWheelMoved(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        mousePos.setLocation(e.getPoint());
        mouseDown = true;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        mousePos.setLocation(e.getPoint());
        mouseDown = false;
    }

    public boolean isMouseDown(){
        return mouseDown;
    }

    public Point2D.Double getMousePos(){
        return new Point2D.Double(mousePos.getX(),mousePos.getY());
    }

    public Point2D getRelativeMousePos(){
        Point2D mouse = getMousePos();
        Camera camera = game.cameraMap.get(CameraID.Main);
        AffineTransform transform = camera.getTransform();
        mouse.setLocation((mouse.getX() - transform.getTranslateX())/transform.getScaleX() , (mouse.getY() - transform.getTranslateY())/transform.getScaleY());
        return mouse;
    }

}
