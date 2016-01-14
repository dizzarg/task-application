 $(function(){
     // this method need for serialize form elements to JSON format
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
       $('.errorMessage').hide();
       console.log('click Delete button');
       var id = $(this).data('id');
       console.log('id='+id);
       $('#deleteModal button.btn-danger').click(function(){
         console.log('click delete button');
         $.ajax({
           url: '/task/'+id,
           type: 'DELETE'
         })
           .done(function() {
             loadAll();
             $('#deleteModal').modal('hide');
           })
           .fail(function() {
             console.log('Cannot delete current task');
             $('.errorMessage').show();
           });
       });
       $('#deleteModal').modal('show');
     });
     $tbl.on('click', "a:contains('View')", function() {
         $('#taskModalLabel').text('View selected Task');
         $('.errorMessage').hide();
         var id = $(this).data('id');
         $.get('/task/'+id, function(data){
           console.log('id='+id+" : "+JSON.stringify(data));

           var $form = $("form[name='task']");
           $form.find('.staticRow').show();
           $form.find('p').show();
           $form.find('p:eq(0)').text(data.id);
           $form.find('p:eq(1)').text(data.name);
           $form.find('p:eq(2)').text(data.description);
           $form.find("*[name]").hide();
           $form.find('p:eq(3)').text(data.createdDate);
           $form.find('p:eq(4)').text(data.modifyDate);

//           $("form[name='task'] .staticRow").show();
//           $("form[name='task'] p:eq(0)").text(data.id);
//           $("form[name='task'] p:eq(1)").show();
//           $("form[name='task'] p:eq(1)").text(data.name);
//           $("form[name='task'] input[name='name']").hide();
//           $("form[name='task'] p:eq(2)").show();
//           $("form[name='task'] p:eq(2)").text(data.description);
//           $("form[name='task'] textarea[name='description']").hide();
//           $("form[name='task'] p:eq(3)").text(data.createdDate);
//           $("form[name='task'] p:eq(4)").text(data.modifyDate);
           $('#taskModal').modal('show');
         });
       });
     $tbl.on('click', "a:contains('Edit')", function() {
     $('#taskModalLabel').text('Edit selected Task');
       $('.errorMessage').hide();
       var id = $(this).data('id');
       $.get('/task/'+id, function(data){
         console.log('id='+id+" : "+ JSON.stringify(data));
         var $form = $("form[name='task']");
         $form.find('.staticRow').show();
         $form.find('p').show();
         $form.find('p:eq(0)').text(data.id);
         $form.find('p:eq(1)').hide();
         $form.find('p:eq(2)').hide();
         $form.find("*[name]").show();
         $form.find("input[name='name']").val(data.name);
         $form.find("textarea[name='description']").val(data.description);
         $form.find('p:eq(3)').text(data.createdDate);
         $form.find('p:eq(4)').text(data.modifyDate);
//         $("form[name='task'] .staticRow").show();
//         $("form[name='task'] p").show();
//         $("form[name='task'] p:eq(0)").text(data.id);
//         $("form[name='task'] p:eq(1)").hide();
//         $("form[name='task'] input[name='name']").show();
//         $("form[name='task'] input[name='name']").val(data.name);
//         $("form[name='task'] p:eq(2)").hide();
//         $("form[name='task'] textarea[name='description']").show();
//         $("form[name='task'] textarea[name='description']").val(data.description);
//         $("form[name='task'] p:eq(3)").text(data.createdDate);
//         $("form[name='task'] p:eq(4)").text(data.modifyDate);

         $form.on('click', "button[type='button']", function(){
             $form.off('submit');
         });
         $form.on('submit',function() {
           console.log(JSON.stringify($form.serializeObject()));
           $.ajax({
              url: "/task/"+id,
              type: 'PUT',
              data: JSON.stringify($form.serializeObject())
             }).done(function() {
                 $('#taskModal').modal('hide');
                 loadAll();
                 $form.off('submit');
             }).fail(function() {
                $('.errorMessage').show();
             });
           return false;
         });
         $('#taskModal').modal('show');
       });
     });
     $('#load').click(function(){
       loadAll();
     });
     $('#create').click(function(){
       $('#taskModalLabel').text('Create new Task');
       $('.errorMessage').hide();
       var $form = $("form[name='task']");
       $form.find('.staticRow').hide();
       $form.find('p').hide();
       $form.find("*[name]").show();
       $form.find("*[name]").val('');
       $form.on('click', "button[type='button']", function(){
           $form.off('submit');
       });
       $form.on('submit',function() {
         console.log(JSON.stringify($form.serializeObject()));
         $.ajax({
            url: "/task/",
            type: 'POST',
            data: JSON.stringify($form.serializeObject())
           }).done(function() {
               $('#taskModal').modal('hide');
               loadAll();
               $form.off('submit');
           }).fail(function() {
              $('.errorMessage').show();
           });
         return false;
       });

       $('#taskModal').modal('show');
     });
     function loadAll(){
       $.get('/task', function(data) {
          console.log(data.length + " loaded");
          var $tblbody = $('table#task TBODY');
          $tblbody.empty();
          $.each(data, function(idx, el){
            $tblbody.append('<tr>');
            $tblbody.append('<td>'+(idx+1)+'</td>');
            $tblbody.append('<td>'+el.name+'</td>');
            $tblbody.append('<td><a class="btn btn-default" id="'+el.id+'" data-id="'+el.id+'" data-toggle="taskModal" href="#">View</a></td>');
            $tblbody.append('<td><a class="btn btn-primary" id="'+el.id+'" data-id="'+el.id+'" data-toggle="taskModal" href="#">Edit</a></td>');
            $tblbody.append('<td><a class="btn btn-danger" id="'+el.id+'" data-id="'+el.id+'" data-toggle="deleteModal" href="#">Delete</a></td>');
            $tblbody.append('</tr>');
          });
       });
     }
});
