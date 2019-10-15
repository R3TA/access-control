/**
 * 
 */

$(document).ready(function() {
	    $.ajax({
	        url: "http://localhost:9000/api/users"
	    }).then(function(data, status, jqxhr) {
	    	//alert(data.length);
	    	var tabla = "";
	    	for(var i = 0;i < data.length;i++){
				tabla = tabla + "<tr>"+
                                    "<td class='align-middle'>"+data[i]["id"]+"</td>"+  
                                    "<td class='align-middle'>"+data[i]["cardNumber"]+"</td>"+  
									"<td class='align-middle'>"+
										"<a href='/api/users/edit?id="+data[i]["id"]+"'><input type='image' src = '/../img/icons/boton-editar.png' alt='"+data[i]["id"]+"'></a>"+
										"&nbsp;&nbsp;&nbsp;&nbsp;"+
										"<input id='id' name='id' type='image' src='/../img/icons/boton-eliminar.png' alt='"+data[i]["id"]+"'></input>"+
									"</td>"+
                               "</tr>";
			}
			$("#grid-data").append(tabla);
	       console.log(jqxhr);
	    });
	});