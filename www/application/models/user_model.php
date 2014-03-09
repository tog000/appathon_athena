<?php
class User_model extends CI_Model {

	public function __construct()
	{
		$this->load->database();
	}

	public function get_user($id = FALSE)
	{
		if ($id === FALSE)
		{
			$query = $this->db->get('user');
			return $query->result();
		}

		$query = $this->db->get('user',array("id"=>$id));
		return $query->result();
	}

	public function get_scoreboard(){
		
		$this->db->from('user');

		$users = array();

		$result = $this->db->get()->result();

		foreach($result as $index=>$user){
			$this->db->from('user_achievement');
			$this->db->where('id_user',$user->id);
			$this->db->join('achievement',"id_achievement = achievement.id");

			$user->achievements = $this->db->get()->result();
			array_push($users, $user);
		}

		return $users;
	}

	public function register_user($user_id)
	{

		if($this->input->post('name')){
			$data = array(
				'id' => $user_id,
				'name' => $this->input->post('name'),
				'avatar' => $this->input->post('avatar'),
				);

			$this->db->from('user');
			$this->db->where('id',$user_id);

			if(count($this->db->get()->result())>0){
				$this->db->where('id', $user_id);
				$this->db->update('user', $data); 
			}else{
				$this->db->insert('user', $data);
			}

			$this->db->from('user');
			$result = $this->db->where('id',$user_id)->get()->result();
			if(count($result) > 0){
				return $result[0];
			}
			return NULL;
		}

	}
}