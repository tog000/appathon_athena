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
		$this->db->join('user_achievement', 'id_achievement = user.id');

		$users = array();

		foreach($this->db->get()->result() as $index=>$user){
			$this->db->from('achievement');
			$this->db->where('id',$user->id_achievement);
			$user->achievements = $this->db->get()->result();
			array_push($users, $user);
		}

		return $users;
	}

	public function set_question()
	{

		$this->load->helper('url');

		$data = array(
			'name' => $this->input->post('name'),
			'avatar' => $this->input->post('avatar'),
			'experience' => 0,
			);

		$this->db->insert('user', $data);

		$insert_id = $this->db->insert_id();

		return $insert_id;

	}
}