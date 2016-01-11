 $(function(){
     $.fn.serializeObject = function() {
         var o = {};
         var a = this.serializeArray();
         $.each(a, function() {
             if (o[this.name] !== undefined) {
                 if (!o[this.name].push) {
                     o[this.name] = [o[this.name]];
                 }
                 o[this.name].push(this.value || '');
             } else {
                 o[this.name] = this.value || '';
             }
         });
         return o;
     };

     var $tbl = $('#tbl');
     $tbl.on('click', "a:contains('Delete')", function() {
       console.log('click Delete button');
       var id = $(this).data('id');
       console.log('id='+id);
       $('#deleteModal button.btn-danger').click(function(){
         console.log('click delete button');
         $.ajax({
           url: '/todo/'+id,
           type: 'DELETE'
         })
           .done(function() {
             loadAll();
           })
           .fail(function() {
           })
           .always(function() {
             $('#deleteModal').modal('hide');
           });
       });
       $('#deleteModal').modal('show');
     });
     $tbl.on('click', "a:contains('View')", function() {
         console.log('Button clicked');
         var id = $(this).data('id');
         console.log('id='+id);
         $.get('/todo/'+id, function(data){
           console.log(data);
            $("form[name='viewContent'] p:eq(0)").text(data.id);
            $("form[name='viewContent'] p:eq(1)").text(data.name);
            $("form[name='viewContent'] p:eq(2)").text(data.desc);
            $("form[name='viewContent'] p:eq(3)").text(data.createdt);
            $("form[name='viewContent'] p:eq(4)").text(data.modifydt);
           $('#viewModal').modal('show');
         });
       });
     $tbl.on('click', "a:contains('Edit')", function() {
       console.log('Button clicked');
       var id = $(this).data('id');
       console.log('id='+id);
       $.get('/todo/'+id, function(data){
         console.log(data);
         $("form[name='editTodo'] p:eq(0)").text(data.id);
         $("form[name='editTodo'] input[name='name']").val(data.name);
         $("form[name='editTodo'] textarea[name='desc']").val(data.desc);
         $("form[name='editTodo'] p:eq(1)").text(data.createdt);
         $("form[name='editTodo'] p:eq(2)").text(data.modifydt);
         var $form = $("form[name='editTodo']");
         $form.on('click', "button[type='button']", function(){
             $form.off('submit');
         });
         $form.on('submit',function() {
           console.log(JSON.stringify($form.serializeObject()));
           $.ajax({
              url: "/todo/"+id,
              type: 'PUT',
              data: JSON.stringify($form.serializeObject())
             }).done(function() {
                 $('#editModal').modal('hide');
                 loadAll();
                 $form.off('submit');
             }).fail(function() {
             });
           return false;
         });
         $('#editModal').modal('show');
       });
     });
     $('#load').click(function(){
       loadAll();
     });
     function loadAll(){
       $.get('/todo', function(data) {
          console.log(data.length + " loaded");
          var $tblbody = $('table#todo TBODY');
          $tblbody.empty();
          $.each(data, function(idx, el){
            $tblbody.append('<tr>');
            $tblbody.append('<td>'+(idx+1)+'</td>');
            $tblbody.append('<td>'+el.name+'</td>');
            $tblbody.append('<td><a class="btn btn-default" id="'+el.id+'" data-id="'+el.id+'" data-toggle="viewModal" href="#">View</a></td>');
            $tblbody.append('<td><a class="btn btn-primary" id="'+el.id+'" data-id="'+el.id+'" data-toggle="editModal" href="#">Edit</a></td>');
            $tblbody.append('<td><a class="btn btn-danger" id="'+el.id+'" data-id="'+el.id+'" data-toggle="deleteModal" href="#">Delete</a></td>');
            $tblbody.append('</tr>');
          });
       });
     }
});