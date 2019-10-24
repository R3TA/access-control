/**
 * 
 */

$(document).ready(function() {
	    $.ajax({
	        url: "/api/assistances"
	    }).then(function(data, status, jqxhr) {
    		var user = JSON.stringify(data);
    		var parser = JSON.parse(user);
    		alert(parser.length);
	    	var tabla = "";
    		
	    	if(parser.length != 0){
		    	for(var i = 0;i < parser.length;i++){
	
					tabla = tabla + "<tr>"+
	                                    "<td class='align-middle'>"+parser[i].id+"</td>"+  
	                                    "<td class='align-middle'>"+parser[i].user.id+"</td>"+
	                                    "<td class='align-middle'>"+parser[i].accessAt+"</td>"+ 
	                                    "<td class='align-middle'>"+parser[i].movimiento+"</td>"+ 
	                               "</tr>";
				}
    		} else {
    			tabla = tabla + "<tr>"+
                					"<td colspan='4' class='align-middle'>Not found results</td>"+  
                				"</tr>";
    		}
	    	
	    	
			$("#grid-data").append(tabla);
	       console.log(jqxhr);
	    });
	});