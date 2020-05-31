using APIGateway.DB;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace APIGateway.Models
{
    public class User
    {
        [NotMapped]
        public string ErrMsg { set; get; }

        [MaxLength(36)]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public string Id { get; set; }

        [Required(AllowEmptyStrings=false, ErrorMessage="This field is required.")]
        public string Username { get; set; }

        [Required(AllowEmptyStrings=false, ErrorMessage="This field is required.")]
        [DataType(DataType.Password)]
        public string Password { get; set; }

        [Required]
        public string Email { get; set; }
    }
}
