/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.DAO;

/**
 *
 * @author TUANANH-LAPTOP
 */
public interface GenericDao<T> {

    public void index();

    public void create();

    public void store();

    public void show();

    public void edit();

    public void update();

    public void destroy();
}
