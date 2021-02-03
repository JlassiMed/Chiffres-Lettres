/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

import DAO.DbConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.list;
import java.util.Comparator;
import static java.util.Comparator.comparing;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static javafx.scene.input.KeyCode.A;

/**
 *
 * @author Mohamed
 */
public class NbEtchifImplements implements NbEtchifInterface {
    DbConnect db= new DbConnect();
    Connection c= db.getConnection();
    PreparedStatement ps;
    ResultSet res;
    @Override
    public List<User> Admins(List<User> all) {
        return all.stream().filter(u-> "A".equals(u.getType_us())).collect(toList());
       /*List<User> L =new ArrayList<>();
        try
        {
             String req="select * from user where type_us='A'";
              ps = c.prepareStatement(req);
              res= ps.executeQuery();
             while(res.next())
             {
             User us= new User();
             us.setPseudo_us(res.getNString("pseudo_us"));
             us.setNom_us(res.getNString("nom_us"));
             us.setPrenom_us(res.getNString("prenom_us"));
             us.setCin_us(res.getNString("cin_us"));
             us.setDatenaiss_us(res.getDate("datenaiss_us"));
             us.setPhoto_us(res.getBytes("photo_us"));
             us.setMeilscore_us(res.getInt("meilscore_us"));
             us.setType_us(res.getString("type_us"));
             L.add(us);
             }
             return L;
        }
        catch(SQLException ex)
        {
                        System.out.println("erreur de selection de donnees "+ex.getMessage());
                        return null;
        }
*/
    }

    @Override
    public List<User> NormalUs(List<User> all) {
       return all.stream().filter(u-> "N".equals(u.getType_us())).collect(toList());
        /*
       List<User> L =new ArrayList<>();
       try
        {
             String req="select * from user where type_us='N'";
             ps = c.prepareStatement(req);
             res= ps.executeQuery();
             while(res.next())
             {
             User us= new User();
             us.setPseudo_us(res.getNString("pseudo_us"));
             us.setNom_us(res.getNString("nom_us"));
             us.setPrenom_us(res.getNString("prenom_us"));
             us.setCin_us(res.getNString("cin_us"));
             us.setDatenaiss_us(res.getDate("datenaiss_us"));
             us.setPhoto_us(res.getBytes("photo_us"));
             us.setMeilscore_us(res.getInt("meilscore_us"));
             us.setType_us(res.getString("type_us"));
             L.add(us);
             }
             return L;
        }
        catch(SQLException ex)
        {
                        System.out.println("erreur de selection de donnees "+ex.getMessage());
                        return null;
        } 
*/
    }

    
    
    @Override
    public List<Dictionnaire> Allmots() {
       List<Dictionnaire> L =new ArrayList<>();
       try
        {
             String req="select * from dictionnaire";
             ps = c.prepareStatement(req);
             res= ps.executeQuery();
             while(res.next())
             {
             Dictionnaire m= new Dictionnaire();
             m.setId_mot(res.getInt("id_mot"));
             m.setValeur_mot(res.getString("valeur_mot"));
             L.add(m);
             }
             return L;
        }
        catch(SQLException ex)
        {
                        System.out.println("erreur de selection de donnees "+ex.getMessage());
                        return null;
        }
    }

    @Override
    public List<Dictionnaire> motsWlength(List<Dictionnaire> l,int length) {
        return l.stream().filter(d -> length==d.getValeur_mot().length()).collect(toList());
    }

    @Override
    public void addUser(User u) {
        try
        {
            String req="insert into user values(?,?,?,?,?,?,?,?,?)";
            ps=c.prepareStatement(req);
                ps.setString(1,u.getPseudo_us());
                ps.setString(2,u.getNom_us());
                ps.setString(3,u.getPrenom_us());
                ps.setString(4,u.getCin_us());
                ps.setDate(5,u.getDatenaiss_us());
                ps.setInt(6,u.getMeilscore_us());
                ps.setBlob(7,u.getPhoto_us());
                ps.setString(8,u.getType_us());
                ps.setString(9,u.getPassword_us());
                
        
            ps.executeUpdate();
        }catch(SQLException e)
        { System.out.println("erreur d'inscription "+e.getMessage()); }
    }

    @Override
    public void DeleteUser(User u) {
     try
        {
            String req="delete from user where pseudo_us=?";
            ps=c.prepareStatement(req);
                ps.setString(1,u.getPseudo_us()); 
        
                ps.executeUpdate();
        }catch(SQLException e)
        { System.out.println(e.getMessage()); }
    }   
    

    @Override
    public void UpdateUser(User u) {
        try{
            String req="update user set nom_us=?,prenom_us=?, cin_us=?, datenaiss_us=?, meilscore_us=?,photo_us=?,type_us=?,password_us=? where pseudo_us=?";
                ps=c.prepareStatement(req);
                ps.setString(1,u.getNom_us());
                ps.setString(2,u.getPrenom_us());
                ps.setString(3,u.getCin_us());
                ps.setDate(4,u.getDatenaiss_us());
                ps.setInt(5,u.getMeilscore_us());
                ps.setBlob(6,u.getPhoto_us());
                ps.setString(7,u.getType_us());
                ps.setString(9,u.getPseudo_us());
                ps.setString(8,u.getPassword_us());
        
        ps.executeUpdate();
        }catch(SQLException e)
        { System.out.println(e.getMessage()); }
   
    }

    @Override
    public User findUser(List<User> all,String pseudo) {
        try{
        return all.stream().filter(u-> u.getPseudo_us().equals(pseudo)).collect(toList()).get(0);
        }catch(Exception e)
        { 
        return null;
        }
       /* 
        List<User> AllUs = this.AllUs();
        User u= new User();
        u.setPseudo_us(pseudo);
        if(!AllUs.contains(u))
         return null;    
        else
        {
        for(User x :AllUs)
        {
        if(x.equals(u))
        {
        u.setNom_us(x.getNom_us());
        u.setPrenom_us(x.getPrenom_us());
        u.setCin_us(x.getCin_us());
        u.setDatenaiss_us(x.getDatenaiss_us());
        u.setMeilscore_us(x.getMeilscore_us());
        u.setPhoto_us(x.getPhoto_us());
        u.setType_us(x.getType_us());
        return u;
        }
        }
        }
        return null;*/
    }

    @Override
    public void addMot(Dictionnaire m) {
         try
        {String req="insert into dictionnaire (valeur_mot) values(?)";
         ps=c.prepareStatement(req);
                ps.setString(1,m.getValeur_mot());
                
       
        ps.executeUpdate();
        }catch(SQLException e)
        { System.out.println(e.getMessage()); }
    }

    @Override
    public void DeleteMot(Dictionnaire m) {
    try
        {
            String req="delete from dictionnaire where id_mot=?";
            ps=c.prepareStatement(req);
                ps.setInt(1,m.getId_mot()); 
        
         ps.executeUpdate();
        }catch(SQLException e)
        { System.out.println(e.getMessage()); }
        
    }

    @Override
    public void UpdateMot(Dictionnaire m) {
         try{
            String req="update dictionnaire set valeur_mot=? where id_mot=?";
                ps=c.prepareStatement(req);
                ps.setString(1,m.getValeur_mot());
                ps.setInt(2,m.getId_mot());
             
        
        ps.executeUpdate();
        }catch(SQLException e)
        { System.out.println(e.getMessage()); }
    }

    
    @Override
    public List<Partie> findParties(List<Partie> all,String pseudo) {
    return all.stream().filter(p-> p.getPseudo_us1().equals(pseudo)||p.getPseudo_us2().equals(pseudo)).collect(toList());
    }

    @Override
    public List<Partie> findParties(List<Partie> all,String pseudoJ1, String pseudoJ2) {
            return all.stream().filter(p-> (p.getPseudo_us1().equals(pseudoJ1)&&p.getPseudo_us2().equals(pseudoJ2))
                    ||(p.getPseudo_us1().equals(pseudoJ2)&&p.getPseudo_us2().equals(pseudoJ1))).collect(toList());
    }

    @Override
    public List<User> TopScoresDesc(List<User> all) {
        return all.stream().sorted(comparing(User::getMeilscore_us)).limit(3).collect(toList());
    }

    @Override
    public User meilleurScoreGlob(List<User> all) {
                return all.stream().max(comparing(User::getMeilscore_us)).get();
    }

    @Override
    public User meilleurScoreCB() {
    List<Partie> all= this.AllParties();
    
    String pseudo;
    Partie x = all.stream().max(comparing(Partie::getJ1ScoreCB)).get();
    Partie y= all.stream().max(comparing(Partie::getJ2ScoreCB)).get();
    if(x.getJ1ScoreCB()>y.getJ2ScoreCB())
    {
    pseudo= x.getPseudo_us1();
    return this.findUser(this.AllUs(),pseudo);
    }
    else if(x.getJ1ScoreCB()<y.getJ2ScoreCB())
    {
    pseudo= x.getPseudo_us2();
    return this.findUser(this.AllUs(),pseudo);
    }
    else// cas d'égalité entre deux meilleur score en CB
        {
    pseudo= x.getPseudo_us1();
    return this.findUser(this.AllUs(),pseudo);
    }
        }

    @Override
    public User meilleurScoreMLP() {
    List<Partie> all= this.AllParties();
    
    String pseudo;
    Partie x = all.stream().max(comparing(Partie::getJ1ScoreMLP)).get();
    Partie y= all.stream().max(comparing(Partie::getJ2ScoreMLP)).get();
    if(x.getJ1ScoreMLP()>y.getJ2ScoreMLP())
    {
    pseudo= x.getPseudo_us1();
    return this.findUser(this.AllUs(),pseudo);
    }
    else if(x.getJ1ScoreMLP()<y.getJ2ScoreMLP())
    {
    pseudo= x.getPseudo_us2();
    return this.findUser(this.AllUs(), pseudo);
    }
    else// cas d'égalité entre deux meilleur score en MLP
        {
    pseudo= x.getPseudo_us1();
    return this.findUser(this.AllUs(),pseudo);
    }
    }

    @Override
    public List<User> AllUs() {
        List<User> all= new ArrayList<User>();
        try
        {
            String req="select * from user";
            ps=c.prepareStatement(req);
            res=ps.executeQuery();
            while(res.next())
            {
             User us= new User();
             
             us.setPseudo_us(res.getString("pseudo_us"));
             us.setNom_us(res.getString("nom_us"));
             us.setPrenom_us(res.getString("prenom_us"));
             us.setCin_us(res.getString("cin_us"));
             us.setDatenaiss_us(res.getDate("datenaiss_us"));
             us.setPhoto_us(res.getBlob("photo_us"));
             us.setMeilscore_us(res.getInt("meilscore_us"));
             us.setType_us(res.getString("type_us"));
             us.setPassword_us(res.getString("password_us"));
             all.add(us);
            }
            
        }catch(SQLException e)
        {
        System.out.println(e.getMessage());
        
        }
        return all;
    }

    

    @Override
    public List<Partie> AllParties() {
    List<Partie> l= new ArrayList();
    try
    {String req="select * from partie";
    ps=c.prepareStatement(req);
    res=ps.executeQuery();
    while(res.next())
    {
    Partie p= new Partie();
    p.setId_partie(res.getInt("id_partie"));
    p.setPseudo_us1(res.getString("pseudo_us1"));
    p.setPseudo_us2(res.getString("pseudo_us2"));
    p.setJ1ScoreMLP(res.getInt("J1ScoreMLP"));
    p.setJ2ScoreMLP(res.getInt("J2ScoreMLP"));
    p.setJ1ScoreCB(res.getInt("J1ScoreCB"));
    p.setJ2ScoreCB(res.getInt("J2ScoreCB"));
    p.setJ1ScoreToT(res.getInt("J1ScoreTot"));
    p.setJ2ScoreTot(res.getInt("J2ScoreTot"));
    p.setDate_partie(res.getDate("date_partie"));
    l.add(p);
    }
    return l;
    }catch(SQLException e)
    {
        System.out.println(e.getMessage());
        return null;
    }
    
    }

    @Override
    public Dictionnaire findMot(List<Dictionnaire> allmots, String val_mot) {
    try
    { return allmots.stream().filter(m-> m.getValeur_mot().equals(val_mot)).collect(toList()).get(0);}
    catch(Exception ex)
    {
    return null;
    }
    
    
    }
    public void AddPartie(Partie p)
    {
        try
        {String req="INSERT INTO partie (pseudo_us1,pseudo_us2,J1ScoreMLP,J1ScoreCB,J2ScoreMLP,J2ScoreCB,J1ScoreTot,J2ScoreTot,date_partie) VALUES (?,?,?,?,?,?,?,?,?)";
    ps=c.prepareStatement(req);
    ps.setString(1,p.getPseudo_us1());
    ps.setString(2, p.getPseudo_us2());
    ps.setInt(3,p.getJ1ScoreMLP());
    ps.setInt(4,p.getJ1ScoreCB());
     ps.setInt(5,p.getJ2ScoreMLP());
    ps.setInt(6,p.getJ2ScoreCB());
    ps.setInt(7, p.getJ1ScoreToT());
    ps.setInt(8, p.getJ2ScoreTot());
    ps.setDate(9, p.getDate_partie());
    ps.executeUpdate();
    
        }catch(SQLException ee)
        {
            System.out.println("insertion impossible de partie"+ee.getMessage());
        }
        
    }
    public List<String> getListAdversaires(User u)
    {
    List<Partie> l= this.AllParties().stream().filter(p-> p.getPseudo_us1().equals(u.getPseudo_us())|| p.getPseudo_us2().equals(u.getPseudo_us())).collect(toList());
    String[] x=new String[200];
    int i=0;
    for(Partie p:l)
    {
    if(p.getPseudo_us1().equals(u.getPseudo_us()))
       x[i]=p.getPseudo_us2();
    else
        x[i]=p.getPseudo_us1();
    
    i++;
    }
    List<String> pss=Arrays.asList(x);
    return pss.stream().distinct().collect(toList());
    }
    
    public int getNombreParties(User u,String adv)
    {
        List<Partie> l= this.AllParties().stream().filter(p-> p.getPseudo_us1().equals(u.getPseudo_us())|| p.getPseudo_us2().equals(u.getPseudo_us())).collect(toList());
        int nb=0;
        for(Partie p:l)
    {
    if(p.getPseudo_us1().equals(u.getPseudo_us()) && p.getPseudo_us2().equals(adv))
            nb++;
    else
         if(p.getPseudo_us1().equals(adv) && p.getPseudo_us2().equals(u.getPseudo_us()))
             nb++;
    }
    return nb;
    }
    
    public int getNombreVictoires(User u,String adv)
    {
        List<Partie> l= this.AllParties().stream().filter(p-> p.getPseudo_us1().equals(u.getPseudo_us())|| p.getPseudo_us2().equals(u.getPseudo_us())).collect(toList());
        int nb=0;
        for(Partie p:l)
    {
    if(p.getPseudo_us1().equals(u.getPseudo_us()) && p.getPseudo_us2().equals(adv))
        if(p.getJ1ScoreToT()>p.getJ2ScoreTot())
            nb++;
    else
         if(p.getPseudo_us1().equals(adv) && p.getPseudo_us2().equals(u.getPseudo_us()))
             if(p.getJ1ScoreToT()<p.getJ2ScoreTot())
             nb++;
    }
    return nb;
    }
    public HashMap<String,Integer> getDatesMelScoreParties(User u)
    {
    List<Partie> l=AllParties().stream().filter(p-> p.getPseudo_us1().equals(u.getPseudo_us())|| p.getPseudo_us2().equals(u.getPseudo_us())).collect(toList());
    List<String> dates=AllParties().stream().filter(p-> p.getPseudo_us1().equals(u.getPseudo_us())|| p.getPseudo_us2().equals(u.getPseudo_us())).map(p->p.getDate_partie().toString()).distinct().collect(toList());
    
    HashMap<String,Integer> hm= new HashMap<>();
    for(String d:dates)
    {
    int max=Integer.MIN_VALUE;
    for(Partie x:l)
    {
    if(x.getDate_partie().toString().equals(d))
    {
    if(x.getPseudo_us1().equals(u.getPseudo_us()))
    {
    if(x.getJ1ScoreToT()>max)
        max=x.getJ1ScoreToT();
    }
    else
    {
    if(x.getPseudo_us2().equals(u.getPseudo_us()))
    {
    if(x.getJ2ScoreTot()>max)
        max=x.getJ2ScoreTot();
    }
    
    }
    
    }
    
    }
    hm.put(d, max);
    }
    return hm;
    }
   
    public int getNombrePartieMois (User u,int mois)
    {
    List<Partie> l= this.AllParties().stream().filter(p-> p.getPseudo_us1().equals(u.getPseudo_us())|| p.getPseudo_us2().equals(u.getPseudo_us())).collect(toList());
    int c=0;
    for(Partie p:l)
    {
    int m= Integer.parseInt(p.getDate_partie().toString().substring(5, 7));
    if(m==mois)
        c++;
    }
    return c;
    }
    public HashMap<Integer,Integer> LineChartData()
    {
        int i; 
        HashMap<Integer,Integer> hm= new HashMap<Integer,Integer>();
        int j=1;
        for(;j<=12;j++)
        {
        hm.put(j, 0);
        }
         for(Entry<Integer,Integer> e:hm.entrySet())
        {
        System.out.println(e.getKey()+" , "+e.getValue());
        }
            List<Partie> lp=this.AllParties();
    for(Partie p:lp)
    {
    int m= Integer.parseInt(p.getDate_partie().toString().substring(5, 7));
    System.out.println(p.getDate_partie().toString().substring(5, 7));
    int old= hm.get(m);
    hm.put(m, old+1);
    }
    return hm;
    }
    
    public HashMap<String,Integer> pieChartData()
    {
    HashMap<String,Integer> hm= new HashMap<>();
    List<User> lu=this.AllUs();
    List<Partie> lp=this.AllParties();
    for(User u:lu)
    {
    //int nbrPartie=(int) lp.stream().filter(p-> p.getPseudo_us1().equals(u.getPseudo_us())||p.getPseudo_us2().equals(u.getPseudo_us()) ).count();
    int nbrPartieGagnes= (int) lp.stream().filter(p-> p.getPseudo_us1().equals(u.getPseudo_us()) || p.getPseudo_us2().equals(u.getPseudo_us() )).filter(p-> {
                Partie pp=(Partie) p; 
                if(pp.getPseudo_us1().equals(u.getPseudo_us()))
                    return pp.getJ1ScoreToT()>pp.getJ2ScoreTot();
                else if(pp.getPseudo_us2().equals(u.getPseudo_us()))
                    return pp.getJ2ScoreTot()>pp.getJ1ScoreToT();
                else
                    return false;
        }).count();
    hm.put(u.getPseudo_us(), nbrPartieGagnes);
    }
    return hm;
    
    }
    
    public List<User> BarChartData()
    {
        
    Comparator<User> cc= (u1,u2)-> Integer.compare(u1.getMeilscore_us(),u2.getMeilscore_us());
    
    List<User> top5us=this.AllUs().stream().sorted((u1,u2)->-cc.compare(u1, u2)).limit(3).collect(toList());
   
    
    return top5us;
    }
    
    public int getNombrePartieGagnee(User u)
    {
    List<Partie> lp=this.AllParties();
    //int nbrPartie=(int) lp.stream().filter(p-> p.getPseudo_us1().equals(u.getPseudo_us())||p.getPseudo_us2().equals(u.getPseudo_us()) ).count();
    int nbrPartieGagnes= (int) lp.stream().filter(p-> p.getPseudo_us1().equals(u.getPseudo_us()) || p.getPseudo_us2().equals(u.getPseudo_us() )).filter(p-> {
                Partie pp=(Partie) p; 
                if(pp.getPseudo_us1().equals(u.getPseudo_us()))
                    return pp.getJ1ScoreToT()>pp.getJ2ScoreTot();
                else if(pp.getPseudo_us2().equals(u.getPseudo_us()))
                    return pp.getJ2ScoreTot()>pp.getJ1ScoreToT();
                else
                    return false;
        }).count();
    return nbrPartieGagnes;
    
    }
    public int getNombrePartie(User u)
    {
        
    return (int) this.AllParties().stream().filter(p-> p.getPseudo_us1().equals(u.getPseudo_us())|| p.getPseudo_us2().equals(u.getPseudo_us())).count();
    
    }
    
    
    public int getMeilleurScMLP(User u)
    {
    List<Partie> p=(List<Partie>) this.AllParties().stream()
            .filter(pp-> pp.getPseudo_us1().equals(u.getPseudo_us())|| pp.getPseudo_us2().equals(u.getPseudo_us())).collect(toList());
    int max= Integer.MIN_VALUE;
    for(Partie partie:p)
    {
    if(partie.getPseudo_us1().equals(u.getPseudo_us()))
    {
    if(partie.getJ1ScoreMLP()>max)
        max=partie.getJ1ScoreMLP();
    }else
        if(partie.getJ2ScoreMLP()>max)
        max=partie.getJ2ScoreMLP(); 
    }
    return max;
    }
    
    public int getMeilleurScCB(User u)
    {
    List<Partie> p=(List<Partie>) this.AllParties().stream()
            .filter(pp-> pp.getPseudo_us1().equals(u.getPseudo_us())|| pp.getPseudo_us2().equals(u.getPseudo_us())).collect(toList());
    int max= Integer.MIN_VALUE;
    for(Partie partie:p)
    {
    if(partie.getPseudo_us1().equals(u.getPseudo_us()))
    {
    if(partie.getJ1ScoreCB()>max)
        max=partie.getJ1ScoreCB();
    }else
        if(partie.getJ2ScoreCB()>max)
        max=partie.getJ2ScoreCB();
    }
    return max;
    }
    
    
}
