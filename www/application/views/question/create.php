<h2>Create a question</h2>

<?php echo validation_errors(); ?>

<?php echo form_open('question/create') ?>

	<label for="question">Question</label>
	<br/>
	<textarea name="question"></textarea><br />

	<label for="answers[]">Option 1</label><br/>
	<input type="checkbox" name="correct" value="0"/>Correct? <input type="input" name="answers[]"/><br />
	<label for="answers[]">Option 2</label><br/>
	<input type="checkbox" name="correct" value="1"/>Correct? <input type="input" name="answers[]"/><br />
	<label for="answers[]">Option 3</label><br/>
	<input type="checkbox" name="correct" value="2"/>Correct? <input type="input" name="answers[]"/><br />
	<label for="answers[]">Option 4</label><br/>
	<input type="checkbox" name="correct" value="3"/>Correct? <input type="input" name="answers[]"/><br />

	<label for="image">Image URL</label>
	<input type="text" name="image"/><br />

	<label for="experience">Experience</label>
	<input type="text" name="experience"/><br />

	<input type="submit" name="submit" value="Create question" />

</form>