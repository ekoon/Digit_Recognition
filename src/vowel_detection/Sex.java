package vowel_detection;

@Table("Sex")
public class Sex extends DbObject<Sex> {

	@Column ("id") @PrimaryKey
	private int    id     = -1;
	
	@Column ("string")
	private String string = null;
	
	public Sex (int id, String string)
	{
		this.id     = id;
		this.string = string;
	}
}