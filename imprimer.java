package paquetImprilac;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.ResultSet;

import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

public class imprimer extends HttpServlet implements Servlet {
	
	

	public void printPDF(){
		
		FacesContext pdf=FacesContext.getCurrentInstance();
		
		try {
			pdf.getExternalContext().dispatch("/imprimerPage.xhtml?partest=buja&par2=125");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
				
			pdf.responseComplete();
		}
	}
	public void doPost(HttpServletRequest requete,HttpServletResponse reponse){
		
		ByteArrayOutputStream op=creationDocumentPDF();
		
		if(requete.getParameter("partest").equals("buja")){
			
			op=this.creationDocumentPDF();
			
			reponse.setHeader("Cache-control", "max-age=30");
			reponse.setContentType("application/pdf");
			reponse.setHeader("content-disposition", "inline;filename=bj.pdf");
			
			reponse.setContentLength(op.size());
			try {
				op.writeTo(reponse.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				op.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (op != null)
			{
			  op.reset();
			}
		}
	}
	private ByteArrayOutputStream creationDocumentPDF(){
		
		ByteArrayOutputStream op=null;
	  PdfWriter secr=null;
		
		Document doc =new Document(PageSize.A4);
		
		op=new ByteArrayOutputStream();
		
		try {
			secr=PdfWriter.getInstance(doc, op);
			
			doc.addTitle("Test d'une page pdf ");
			doc.addCreationDate();
			doc.addAuthor("Bukuru Floris");
			doc.addProducer();
			
			doc.open();
			
			//======
			
			String nom = "KANA Jean", nif = "123AB", societe = "BRARUDI";
			
			try {
				InitialContext ct = new InitialContext();
				
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//======
			
			doc.add(new Paragraph("                                                                FACTURE PROFORMA"));
			doc.add(new Paragraph("                                                                "));
			doc.add(new Paragraph("                                                                "));
			doc.add(new Paragraph("Nom du client: "+nom+"   NIF: "+nif+"  SOCIETE: "+societe+" "));
			
			doc.add(createTable());
		
		
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		doc.close();
			
			return op;

		}

	private Element createTable(){
		
		Table db=null;
		Cell col=null;
		int widthCollone[]={10,30,40,30,30,30};
		  try {
			db=new Table(6);
			
			db.setWidths(widthCollone);
			db.setPadding(2);
			db.addCell(creerCellule("N°"));
			db.addCell(creerCellule("Type"));
			db.addCell(creerCellule("Nom"));
			db.addCell(creerCellule("Quantite"));
			db.addCell(creerCellule("PU"));
			db.addCell(creerCellule("PT"));
	
			//String req="select nomPersonne,prenomPersonne,telPersonne from personne order by nomPersonne";
			//ResultSet res=Connecteur.selectData(req);
			/*try {
				while(res.next()){
					this.nom=res.getString("nomPersonne");
					this.prenom=res.getString("prenomPersonne");
					this.tel=res.getString("telPersonne");
					db.addCell(creerCellule(""+this.nom));
					db.addCell(creerCellule(""+this.prenom));
					db.addCell(creerCellule(""+this.tel));
					col=new Cell(""+this.nom);
					db.addCell(col);
					col=new Cell(""+this.prenom);
					
					db.addCell(col);
					col=new Cell(""+this.tel);
					db.addCell(col);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		} catch (BadElementException e) {
		
			e.printStackTrace();
			
		*/}catch (DocumentException e) {
			
			e.printStackTrace();
		}
		
		return db;
	}
public Cell creerCellule(String nom) {
    Cell cellule = new Cell(nom);
    cellule.setHeader(true);
    cellule.setBackgroundColor(Color.WHITE.brighter().brighter().brighter());
    cellule.setHorizontalAlignment(Element.ALIGN_CENTER);
    
    return cellule;
}
public Cell creerTitle(String nom) {
    Cell cellule = new Cell(nom);
    cellule.setHeader(true);
    cellule.setBackgroundColor(Color.WHITE.brighter().brighter().brighter());
    cellule.setHorizontalAlignment(Element.ALIGN_CENTER);
   
    return cellule;
}








}
