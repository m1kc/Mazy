/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazy;

/**
 *
 * @author m1kc
 */
public interface Observable {
    public void subscribe(Observer x);
    public void ring();
}
