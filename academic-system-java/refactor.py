import os
import re

directory = '.'

replacements = {
    'AcademicSystemController': 'ControladorSistemaAcademico',
    'AcademicSystemException': 'ExcecaoSistemaAcademico',
    'SecuritySystemException': 'ExcecaoSegurancaSistema',
    'AuthorizationException': 'ExcecaoAutorizacao',
    'AuthenticationException': 'ExcecaoAutenticacao',
    'KeyboardInputException': 'ExcecaoEntradaTeclado',
    'DomainValidator': 'ValidadorDominio',
    'TurmaService': 'ServicoTurma',
    'TurmaController': 'ControladorTurma',
    'AvaliacaoController': 'ControladorAvaliacao',
    'AcademicSystem': 'SistemaAcademico',
    'RegistrodeAvaliacaoTest': 'RegistroDeAvaliacaoTeste',
    'RegistrodeTurmasTest': 'RegistroDeTurmaTeste',
}

# First, rename files
for root, dirs, files in os.walk(directory):
    for file in files:
        if file.endswith('.java'):
            old_name = file[:-5]
            if old_name in replacements:
                new_name = replacements[old_name]
                old_path = os.path.join(root, file)
                new_path = os.path.join(root, new_name + '.java')
                os.rename(old_path, new_path)
                print(f"Renamed {old_path} to {new_path}")

# Second, replace contents
for root, dirs, files in os.walk(directory):
    for file in files:
        if file.endswith(('.java', '.xml', '.md')):
            filepath = os.path.join(root, file)
            with open(filepath, 'r', encoding='utf-8') as f:
                content = f.read()
            
            new_content = content
            for old, new in replacements.items():
                # Replace with word boundaries to avoid partial matches
                new_content = re.sub(r'\b' + re.escape(old) + r'\b', new, new_content)
                
                # Also replace camelCase variable instances if they exist
                old_var = old[0].lower() + old[1:]
                new_var = new[0].lower() + new[1:]
                new_content = re.sub(r'\b' + re.escape(old_var) + r'\b', new_var, new_content)

            if content != new_content:
                with open(filepath, 'w', encoding='utf-8') as f:
                    f.write(new_content)
                print(f"Updated content in {filepath}")

