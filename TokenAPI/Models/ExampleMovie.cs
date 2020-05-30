using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace TokenAPI.Models
{
    public class ExampleMovie
    {
        [MaxLength(36)]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public string Id { get; set; }

        [Required]
        [MaxLength(128)]
        public string Title { get; set; }

        [NotMapped]// results in IsPopular property not appearing as a column in the Movie table
        public bool IsPopular { get; set; }

    }
}
