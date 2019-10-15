/**
 * 
 */
$(document).ready(function() {
	    $.ajax({
	        url: "http://localhost:9000/api/assistances"
	    }).then(function(data, status, jqxhr) {
    		var user = JSON.stringify(data);
    		var parser = JSON.parse(user);
    		alert(parser.length);
	    	var tabla = "";
	    	for(var i = 0;i < parser.length;i++){

				tabla = tabla + "<tr>"+
                                    "<td class='align-middle'>"+parser[i].id+"</td>"+  
                                    "<td class='align-middle'>"+parser[i].user.id+"</td>"+
                                    "<td class='align-middle'>"+parser[i].accessAt+"</td>"+ 
                                    "<td class='align-middle'>"+parser[i].movimiento+"</td>"+ 
                               "</tr>";
			}
			$("#grid-data").append(tabla);
	       console.log(jqxhr);
	    });
	});