/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Andressa
 */
public enum StatusConnectGuides {
    /*Regra deste status (depende dos status de search dos usuários)
    Found         -> Found ^ Found
    WaitingAnswer -> WaitingAnswer v Found; WaitingAnswer v Accepted
    Accepted      -> Accepted ^ Accepted
    Rejected      -> Rejected v Found; Rejected v WaitingAnswer
    */
    Found, WaitingAnswer, Accepted, Rejected;
}
