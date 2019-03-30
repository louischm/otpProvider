namespace OTP_SP.Helpers
{
    public static class SQLHelper
    {
        public const string sqlConnectionString = @"Server=tcp:otp-sp20190329105535dbserver.database.windows.net,1433;Initial Catalog=OTP-SP20190329105535_db;Persist Security Info=False;User ID=scrapy;Password=adri_seg1;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;";

        public static void RecordUser(Models.UserCreation user)
        {
            string sqlCmdTxt = "SELECT count(*) FROM USERS;";

            using (var sqlCon = new System.Data.SqlClient.SqlConnection(sqlConnectionString))
            using (var sqlCmd = new System.Data.SqlClient.SqlCommand(sqlCmdTxt, sqlCon))
            {
                sqlCon.Open();
                int idx = (int)sqlCmd.ExecuteScalar(); // UGLY

                sqlCmd.CommandText = "INSERT INTO USERS (ID, NAME, PASSHASH) VALUES (@ID, @NAME, @PASSHASH);";

                var id = sqlCmd.Parameters.Add("@ID", System.Data.SqlDbType.Int);
                id.Value = idx + 1;

                var name = sqlCmd.Parameters.Add("@NAME", System.Data.SqlDbType.VarChar);
                name.Value = user.UserName;

                var passHash = sqlCmd.Parameters.Add("@PASSHASH", System.Data.SqlDbType.VarBinary);
                passHash.Value = System.Security.Cryptography.SHA256.Create().ComputeHash(System.Text.Encoding.UTF8.GetBytes(user.Password));

                sqlCmd.ExecuteNonQuery();
                sqlCon.Close();
            }
        }

        public static bool CheckUserCreds(Models.UserCreation user)
        {
            string sqlCmdTxt = $"SELECT * FROM USERS WHERE NAME = '{user.UserName}';";

            using (var sqlCon = new System.Data.SqlClient.SqlConnection(sqlConnectionString))
            using (var sqlCmd = new System.Data.SqlClient.SqlCommand(sqlCmdTxt, sqlCon))
            {
                sqlCon.Open();
                byte[] inputPassHash;
                byte[] sqlPassHash;
                using (var sqlReader = sqlCmd.ExecuteReader())
                {
                    if (!sqlReader.HasRows)
                    {
                        sqlCon.Close();
                        return false;
                    }
                    sqlReader.Read();
                    inputPassHash = System.Security.Cryptography.SHA256.Create().ComputeHash(System.Text.Encoding.UTF8.GetBytes(user.Password));
                    sqlPassHash = (byte[])sqlReader.GetValue(2);
                }
                sqlCon.Close();

                return System.Collections.StructuralComparisons.StructuralEqualityComparer.Equals(inputPassHash, sqlPassHash);
            }
        }
    }
}